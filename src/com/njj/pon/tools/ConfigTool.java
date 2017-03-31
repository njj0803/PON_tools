package com.njj.pon.tools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigTool {
	public Map<String,String> readConfig() {
		Map<String,String> map = null;
		File file = new File("config.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<?> list = root.elements();
		if (checkConfig(list)){
			map = new HashMap<>();
			for (Object obj : list){
				Element element = (Element) obj;
				String name = element.getName();
				String text = element.getText();
				map.put(name, text);
			}
		}
		return map;
	}

	private boolean checkConfig(List<?> list) {
		boolean flag = true;
		for (Object obj : list) {
			Element element = (Element) obj;
			String name = element.getName();
			String text = element.getText().toLowerCase();
			if (name.equals("pon")) {
				if (!text.equals("epon") && !text.equals("gpon")) {
					flag = false;
					break;
				}
			}
			if (name.equals("multile")) {
				if (!text.equals("true") && !text.equals("false")) {
					flag = false;
					break;
				}

			}
			if (name.equals("logpath")) {
				if (!text.endsWith(".log")) {
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
}
