package com.njj.pon.tools;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigTool {

	private Map<String, String> getMap(List<?> list) {

		Map<String, String> map = new LinkedHashMap<>();
		for (Object obj : list) {
			Element element = (Element) obj;
			String name = element.getName();
			String text = element.getText();
			map.put(name, text);
		}
		return map;

	}

	private List<?> getXML(String pon) {
		File file = null;
		if (pon.equals("epon")) {
			file = new File("epon.xml");
		} else if (pon.equals("gpon")) {
			file = new File("gpon.xml");
		} else {
			System.out.println("参数错误(未知的PON类型)，程序自动退出！");
		}
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<?> list = root.elements();
		return list;
	}

	public Map<String, String> readGponConfig() {
		Map<String, String> map = null;
		List<?> list = getXML("gpon");
		if (checkGponConfig(list)) {
			map = getMap(list);

		}

		return map;

	}

	public Map<String, String> readEponConfig() {
		Map<String, String> map = null;
		List<?> list = getXML("epon");
		if (checkEponConfig(list)) {
			map = getMap(list);
		}
		return map;
	}

	private boolean checkEponConfig(List<?> list) {
		boolean flag = true;
		for (Object obj : list) {
			Element element = (Element) obj;
			String name = element.getName();
			String text = element.getText().toLowerCase();
			if (name.equals("pon")) {
				if (!text.equals("epon")) {
					flag = false;
					break;
				}
			}
			if (name.equals("oldport")) {
				String[] ports = text.split("-");
				if (!(ports.length == 4 && ports[0].equals("1") && ports[1].equals("1"))) {
					flag = false;
					break;
				}
			}
			if (name.equals("newport")) {
				String[] ports = text.split("/");
				if (!(ports.length == 3 && ports[0].equals("0"))) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	private boolean checkGponConfig(List<?> list) {
		boolean flag = true;
		for (Object obj : list) {
			Element element = (Element) obj;
			String name = element.getName();
			String text = element.getText().toLowerCase();
			if (name.equals("pon")) {
				if (!text.equals("gpon")) {
					flag = false;
					break;
				}
			}
			if (name.equals("oldport")) {
				String[] ports = text.split("/");
				if (!(ports.length == 4 && ports[0].equals("1") && ports[1].equals("1"))) {
					flag = false;
					break;
				}
			}
			if (name.equals("newport")) {
				String[] ports = text.split("/");
				if (!(ports.length == 3 && ports[0].equals("0"))) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

}
