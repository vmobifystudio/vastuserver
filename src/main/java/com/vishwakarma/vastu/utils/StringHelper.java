package com.vishwakarma.vastu.utils;

public class StringHelper {

	public static String arrayToCsv (Object[] elems) {
		
		if (null==elems || 0==elems.length) {
			return "";
		}
		
		StringBuffer buf = new StringBuffer();
		for (Object obj : elems) {
			buf.append(obj.toString()).append(",");
		}
		if (buf.length()>0) {
			return buf.substring(0, buf.length()-1);
		}
		else {
			return buf.toString();
		}
	}
}
