package com.njj.pon.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.njj.pon.beans.ONUInfo;

public class Tool {

	public void splitFile(String path,int oldSvlan) {

		try {
			String encoding = "utf-8";

			List<String> list = new ArrayList<>();
			List<String> secList = new ArrayList<>();
			List<Integer> flags = new LinkedList<>();
			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					if (line.contains("ONT-1-1-") && line.contains("MACADDR")) {
						String number = line.substring(4, 18).replace(':', ' ').trim();
						list.add(number);
					}
					if (line.contains("LOGICALAUID")) {
						String loid = line.substring(17, 34).replace('\\', ' ').trim();
						list.add(loid);
					}
					if (line.contains("OLTFLOW-1-1") && line.contains("SVLAN="+oldSvlan)){
						int index = line.lastIndexOf(',');
						String vlan = line.substring(12).replace(",", "::");
						String[] strs = vlan.split("::");
						String id = strs[0];
						String cvlan =strs[2];
						String svlan = strs[3];
						list.add(id+":"+cvlan+":"+svlan);
					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
			for (String str : list) {
				System.out.println(str);
			}
			

		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	public void readLog(String path) {
		try {
			String encoding = "utf-8";

			List<ONUInfo> mapList = new ArrayList<>();
			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.contains("ONT-1-1-") && lineTxt.contains("MACADDR")) {
						ONUInfo onu = new ONUInfo();
						String loid = lineTxt.substring(4, 18).replace(':', ' ').trim();
						System.out.println(loid);
						onu.setLoid(loid);
					}

				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

}
