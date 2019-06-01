package com.greenleaves.tools;

import java.util.HashMap;
import java.util.Map;

public class Response {
	
	public static Map<String, Object> success(Object message) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);
		res.put("message", message);
		return res;
	}

	public static Map<String, Object> fail(Object message) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		res.put("message", message);
		return res;
	}
	
	public static Map<String, Object> quit() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", -1);
		res.put("message", "Please login first");
		return res;
	}
	
}
