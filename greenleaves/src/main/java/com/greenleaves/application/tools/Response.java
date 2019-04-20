package com.greenleaves.application.tools;

public class Response {

	public static String message(int code, String msg) {
		String message = msg.indexOf("{")>-1 && msg.indexOf("}")>-1 ? msg : "\""+msg+"\"";
		return "{\"code\":"+code+",\"message\":"+message+"}";
	}
	
}
