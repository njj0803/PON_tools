package com.njj.pon.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GponTool {

	private List<String> outLoidList = new LinkedList<>();
	private List<String> outServicePortList = new LinkedList<>();
	private List<String> outBTVList = new LinkedList<>();
	private List<String> outSpeedList = new LinkedList<>();

	/**
	 * @param file
	 *            日志文件
	 * @param id
	 *            包含loid的列表
	 * @param vlan
	 *            包含cvlan的列表
	 * @param newSvlan
	 *            新的svlan
	 * @param newport
	 *            新的PON口
	 * @param servicePort
	 *            起始svlan
	 * @throws FileNotFoundException
	 *             文件无法读取异常
	 */
	private void exeString(File file, List<String> id, List<String> vlan, String newSvlan, String newport,
			String servicePort) throws FileNotFoundException {
		String[] ports = newport.split("/");
		String inter = "interface gpon " + ports[0] + "/" + ports[1] + "\n";
		String quit = "quit\n";
		outLoidList.add(inter);
		for (int i = 0; i < id.size(); i++) {
			String speed = "traffic-limit ont " + newport + " " + i + "  down-stream traffic-table index 100\n";
			outSpeedList.add(speed);
			String loid = id.get(i).split(":")[1];
			StringBuilder sb = new StringBuilder();
			sb.append("ont add ");
			sb.append(ports[2]);
			sb.append(" " + i + " ");
			sb.append("loid-auth \"");
			sb.append(loid + "\" ");
			sb.append("always-on omci ont-lineprofile-id 200  ont-srvprofile-id 1");
			sb.append("\n");
			outLoidList.add(sb.toString());
		}
		outLoidList.add(quit);

		for (int i = 0; i < id.size(); i++) {
			String cvlan = vlan.get(i).split(":")[2];
			StringBuilder servicePortVlan = new StringBuilder();
			StringBuilder servicePort32 = new StringBuilder();
			StringBuilder servicePort43 = new StringBuilder();
			StringBuilder servicePort42 = new StringBuilder();
			StringBuilder servicePort45 = new StringBuilder();
			int servicevlan = Integer.parseInt(servicePort) + (i * 5);
			int service32 = Integer.parseInt(servicePort) + (i * 5) + 1;
			int service43 = Integer.parseInt(servicePort) + (i * 5) + 2;
			int service42 = Integer.parseInt(servicePort) + (i * 5) + 3;
			int service45 = Integer.parseInt(servicePort) + (i * 5) + 4;
			int svlan43 = Integer.parseInt(newSvlan) + 1600;
			int svlan45 = Integer.parseInt(newSvlan) + 800;
			int tmpsvlan42 = Integer.parseInt(newSvlan) + 2400;
			int svlan42 = tmpsvlan42 > 3000 ? tmpsvlan42 + 100 : tmpsvlan42;
			servicePortVlan.append("service-port ");
			servicePortVlan.append(servicevlan + " vlan ");
			servicePortVlan.append(newSvlan + "  gpon ");
			servicePortVlan.append(newport + " ont ");
			servicePortVlan.append(i);
			servicePortVlan.append(" gemport 0 multi-service user-vlan ");
			servicePortVlan.append(cvlan);
			servicePortVlan.append(" tag-transform translate-and-add inner-vlan ");
			servicePortVlan.append(cvlan);
			servicePortVlan.append(" inbound traffic-table index 201 outbound traffic-table index 201");
			servicePortVlan.append("\n");
			outServicePortList.add(servicePortVlan.toString());
			servicePort32.append("service-port ");
			servicePort32.append(service32 + " vlan ");
			servicePort32.append(newSvlan + "  gpon ");
			servicePort32.append(newport + " ont ");
			servicePort32.append(i);
			servicePort32.append(
					" gemport 0 multi-service user-vlan 32 tag-transform translate-and-add inner-vlan 32 inbound traffic-table index 201 outbound traffic-table index 201\n");
			outServicePortList.add(servicePort32.toString());
			servicePort43.append("service-port ");
			servicePort43.append(service43 + " vlan ");
			servicePort43.append(svlan43 + "  gpon ");
			servicePort43.append(newport + " ont ");
			servicePort43.append(i);
			servicePort43.append(
					" gemport 1 multi-service user-vlan 43 tag-transform translate-and-add inner-vlan 43 inbound traffic-table index 202 outbound traffic-table index 202\n");
			outServicePortList.add(servicePort43.toString());
			servicePort42.append("service-port ");
			servicePort42.append(service42 + " vlan ");
			servicePort42.append(svlan42 + "  gpon ");
			servicePort42.append(newport + " ont ");
			servicePort42.append(i);
			servicePort42.append(
					" gemport 2 multi-service user-vlan 42 tag-transform translate-and-add inner-vlan 42 inbound traffic-table index 203 outbound traffic-table index 203\n");
			outServicePortList.add(servicePort42.toString());
			servicePort45.append("service-port ");
			servicePort45.append(service45 + " vlan ");
			servicePort45.append(svlan45 + "  gpon ");
			servicePort45.append(newport + " ont ");
			servicePort45.append(i);
			servicePort45.append(
					" gemport 3 multi-service user-vlan 45 tag-transform translate-and-add inner-vlan 45 inbound traffic-table index 204 outbound traffic-table index 204\n");
			outServicePortList.add(servicePort45.toString());
		}

		outBTVList.add("btv\n");
		for (int i = 0; i < id.size(); i++) {
			int index = Integer.parseInt(servicePort) + (i * 5) + 2;
			StringBuilder btv = new StringBuilder();
			btv.append("igmp user add service-port  ");
			btv.append(index);
			btv.append(" no-auth quickleave mac-based\n");
			if (i == id.size() - 1) {
				btv.append("\r");
				btv.append(quit);
			}
			outBTVList.add(btv.toString());
		}
		outBTVList.add("multicast-vlan 40\n");
		for (int i = 0; i < id.size(); i++) {
			int index = Integer.parseInt(servicePort) + (i * 5) + 2;
			StringBuilder mul = new StringBuilder();
			mul.append("igmp multicast-vlan member service-port  ");
			mul.append(index);
			mul.append("\n");
			if (i == id.size() - 1) {
				mul.append("\r");
				mul.append(quit);
			}
			outBTVList.add(mul.toString());

		}
		// 写入文件
		PrintStream ps = new PrintStream(new FileOutputStream(file));
		for (String str : outLoidList) {
			ps.println(str);
		}
		for (String str : outServicePortList) {
			ps.println(str);
		}
		for (String str : outSpeedList) {
			ps.println(str);
		}
		for (String str : outBTVList) {
			ps.println(str);
		}
		ps.close();

	}

	private boolean writeToFile(String dir, String name, List<String> id, List<String> vlan, String newSvlan,
			String newport, String servicePort) throws IOException {
		boolean flag = false;
		File dirFile = new File(dir);
		// 如果文件夹不存在则创建
		if (!dirFile.exists() && !dirFile.isDirectory()) {
			dirFile.mkdir();
		}
		File file = new File(dir + name);
		if (!file.exists()) {
			file.createNewFile();
			exeString(file, id, vlan, newSvlan, newport, servicePort);
			flag = true;

		}

		return flag;
	}

	/**
	 * @param id
	 *            id列表
	 * @param vlan
	 *            vlan列表
	 * @return flag 两列表loid匹配，返回true
	 */
	private boolean isOK(List<String> id, List<String> vlan) {
		boolean flag = false;
		if (id.size() == vlan.size()) {
			flag = true;
		}

		return flag;
	}

	public void singleGpon(Map<String, String> map) throws UnsupportedEncodingException {
		String oldSvlan = map.get("oldsvlan");
		String newSvlan = map.get("newsvlan");
//		String oldPort = map.get("oldport");
		String newPort = map.get("newport");
		String servicePort = map.get("serviceport");
		File file = new File("session.log");
		try {
			String encoding = "utf-8";
			List<String> list = new ArrayList<>();
			List<String> idList = new ArrayList<>();
			List<String> vlanList = new ArrayList<>();

			if (file.isFile() && file.exists()) {
				System.out.println("正在读取日志文件...");
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					if (line.contains("interface 1/1") && line.contains("sw-ver-pland")) {
						String number = line.trim().split(" ")[1];
						list.add(number);
					}
					if (line.contains("log-auth-id")) {
						String loid = line.trim().split(" ")[1];
						list.add(loid);
					}
					if (line.contains("network-vlan stacked:") && line.contains(oldSvlan)) {
						String vlan = line.trim().split(" ")[1];
						vlanList.add(vlan);
					}
				}
				read.close();

			} else {
				System.out.println("没有找到日志文件,程序结束！");
				System.exit(0);
			}
			String tmp = "";
			for (int i = 0; i < list.size(); i++) {

				if (i % 2 == 0) {
					tmp += list.get(i);
					tmp += ":";
				} else {
					tmp += list.get(i);
					idList.add(tmp);
					tmp = "";
				}

			}
			for (String str : idList) {
				System.out.println(str);
			}
			for (String str : vlanList) {
				System.out.println(str);
			}
			System.out.println("日志文件读取成功，正在比对信息...");
			if (isOK(idList, vlanList)) {
				System.out.println("信息比对成功，正在生成割接脚本...");

				String dir = "output/";
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String name = "gpon_"+df.format(new Date()) + ".txt";

				if (writeToFile(dir, name, idList, vlanList, newSvlan, newPort, servicePort)) {
					System.out.println("生成脚本成功！");
					System.out.println(dir + name);
				} else {
					System.out.println("生成脚本失败！程序自动退出！");
					System.exit(0);
				}
			} else {
				System.out.println("信息比对失败，请检查日志文件！程序自动退出！");
				System.exit(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
