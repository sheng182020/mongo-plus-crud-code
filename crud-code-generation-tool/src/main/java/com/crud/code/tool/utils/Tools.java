package com.crud.code.tool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
	
	public static void log(Object obj) {
		if(obj == null) {
			System.out.println("null");
		} else {
			//System.out.println(obj.toString());
		}
	}
	
	public static void log(Object ...objects) {
		if(objects == null) {
			System.out.println("null");
		}
		for(Object obj : objects) {
			//log(obj);
		}
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals("");
	}
	
	public static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATETIME_FORMATTER_NO_LINE = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public static String nowNoLine() {
		
		return DATETIME_FORMATTER_NO_LINE.format(new Date());
	}

	public static String now() {
		
		return DATETIME_FORMATTER.format(new Date());
	}
	
	public static String upperFirst(String str) {
		
		if(str == null || str.length() == 0) {
			return str;
		}
		
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	
	public static String lowwerFirst(String str) {
		
		if(str == null || str.length() == 0) {
			return str;
		}
		
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	
	public static String toHump(String underLine) {

		if (underLine == null || underLine.isEmpty()) {
			return underLine;
		}

		StringBuilder result = new StringBuilder();
		// 用正则表达式将下划线、短横线和空格作为分隔符
		String[] parts = underLine.split("[-_ ]");

		for (int i = 0; i < parts.length; i++) {
			result.append(parts[i].substring(0, 1).toUpperCase());
			result.append(parts[i].substring(1).toLowerCase());
		}

		return result.toString();
	}

	public static String getUpPath(String input) {
		int lastIndex = input.lastIndexOf(".");

		if (lastIndex != -1) {
			String result = input.substring(0, lastIndex);
			return result;
		} else {
			throw new RuntimeException("路径不对");
		}
	}
	
	public static void main(String[] args) {
		
		// String underLine = "ap_ddd_sss_ddg";
		// String hump = toHump(underLine);
		// System.out.println(hump);
		//
		// System.out.println(lowwerFirst(hump));
	}
	
}
