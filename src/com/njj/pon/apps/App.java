package com.njj.pon.apps;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

import com.njj.pon.tools.EponTool;
import com.njj.pon.tools.GponTool;
import com.njj.pon.tools.ConfigTool;

public class App {

	// 最大输错次数
	public static final int MAX_TIMES = 5;

	public static void main(String[] args) throws UnsupportedEncodingException {
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
			System.out.println("2." + "7360->5680 单PON口割接");
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
				Map<String, String> map = conf.readEponConfig();
				if (map != null) {
					EponTool tool = new EponTool();
					tool.singleEpon(map);
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - starTime;
					System.out.println("成功生成脚本，总共耗时：" + totalTime + "ms");
					break;

				} else {
					System.out.println("读取配置文件错误,程序自动退出！");
					System.exit(0);
				}

			} else if (chose1.equals("2")) {
				System.out.println("2.7360->5680单PON口割接");
				long starTime = System.currentTimeMillis();
				ConfigTool conf = new ConfigTool();
				Map<String, String> map = conf.readGponConfig();
				if (map != null) {
					GponTool tool = new GponTool();
					tool.singleGpon(map);
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - starTime;
					System.out.println("成功生成脚本，总共耗时：" + totalTime + "ms");
					break;

				} else {
					System.out.println("读取配置文件错误,程序自动退出！");
					System.exit(0);
				}

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
