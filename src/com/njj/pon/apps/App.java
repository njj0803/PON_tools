package com.njj.pon.apps;

import com.njj.pon.tools.Tool;

public class App {

	public static void main(String[] args) {
		 long starTime=System.currentTimeMillis();
		Tool tool = new Tool();
		String path = "C:\\Users\\njj08\\Desktop\\启东跳板_2017-03-29_19-39-16.log";
		tool.splitFile(path,762);
		 long endTime=System.currentTimeMillis();
		 long totalTime = endTime - starTime; 
		 System.out.println("成功生成脚本，总共耗时："+totalTime+"ms");
	}

}
