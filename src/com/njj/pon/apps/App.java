package com.njj.pon.apps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.njj.pon.tools.Tool;
import com.njj.pon.tools.ConfigTool;

public class App {

	// 最大输错次数
	public static final int MAX_TIMES = 5;

	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy MM-dd");
		String dd = df.format(new Date());
		System.out.println("********************" + "PON口割接工具Beta1.0-命令行版" + "********************");
		System.out.println("		Build " + dd + " 作者：倪晶晶");
		System.out.println("*****************************************************************");
		int tryTimes = 0;
		while (true) {
			if (tryTimes == MAX_TIMES) {
				System.out.println("已达到最大输入次数，程序自动退出！");
				break;
			}
			System.out.println("按照提示输入正确的数字：(输入Q或者q退出程序)");
			System.out.println("1." + "7342->5680 单PON口割接");
			System.out.println("2." + "7342->5680 多PON口割接");
			System.out.println("3." + "7360->5680 单PON口割接");
			System.out.println("4." + "7360->5680 多PON口割接");
			System.out.print("请选择：");
			Scanner sc = new Scanner(System.in);
			String chose1 = sc.nextLine();
			if (chose1.equals("Q") || chose1.equals("q")) {
				System.out.println("*****ByeBye!*****");
				break;
			}

			if (chose1.equals("1")) {
				System.out.println("1.7342->5680 单PON口割接");

				long starTime = System.currentTimeMillis();
				ConfigTool conf = new ConfigTool();
				Map<String, String> map = conf.readConfig();
				if (map != null) {
					Tool tool = new Tool();
					tool.singleEpon(map);
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - starTime;
					System.out.println("成功生成脚本，总共耗时：" + totalTime + "ms");

					break;

				} else {
					System.out.println("读取配置文件错误,程序自动退出！");
					System.exit(0);
				}

				// System.out.print("请输入CRT记录的日志文件路径：");
				// String path = sc.nextLine();
				// System.out.print("请输入旧的外层VLAN：");
				// String oldSvlan = sc.nextLine();
				// System.out.print("请输入新的外层VLAN：");
				// String newSvlan = sc.nextLine();
				// System.out.print("请输入旧的PON口(例：1-1-2-1)：");
				// String oldPort = sc.nextLine();
				// System.out.print("请输入新的PON口(例:0/2/1)：");
				// String newport = sc.nextLine();
				// System.out.print("请输入起始的ServicePort：");
				// String servicePort = sc.nextLine();

				// Tool tool = new Tool();
				// tool.singleEpon(path, oldSvlan, newSvlan, oldPort, newport,
				// servicePort);

			} else if (chose1.equals("2")) {
				System.out.println("2.7342->5680 多PON口割接");
				System.out.println("功能开发中，敬请期待...");
				break;
			} else if (chose1.equals("3")) {
				System.out.println("3.7360->5680单PON口割接");
				String path = "‪C:\\Users\\倪晶晶\\Desktop\\session.log";
				System.out.println("功能开发中，敬请期待...");
				break;
			} else if (chose1.equals("4")) {
				System.out.println("4.7360->5680 多PON口割接");
				System.out.println("功能开发中，敬请期待...");
				break;
			} else {
				if (tryTimes < MAX_TIMES - 1) {
					System.out.println("输入有误，请重新输入!");
				}

				tryTimes++;
			}
			sc.close();

		}

	}

}
