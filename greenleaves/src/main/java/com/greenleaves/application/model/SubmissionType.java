package com.greenleaves.application.model;

public class SubmissionType {
	
	private int id;
	private String name;
	private String type;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		String str = "{";
		str += "\"id\":"+id+",";
		str += "\"name\":\""+name+"\",";
		str += "\"type\":\""+type+"\"";
		str += "}";
		return str;
	}
	
}
