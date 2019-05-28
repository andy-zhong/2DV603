package com.greenleaves.application.model;

public class Schedule {

	private int id;
	private String title;
//	private String content;
	private int startTime;
	private int endTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
//	public String getContent() {
//		return content;
//	}
	
//	public void setContent(String content) {
//		this.content = content;
//	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public String toString() {
		String str = "{";
		str += "\"id\":"+id+",";
		str += "\"title\":\""+title+"\",";
		//str += "\"content\":\""+content+"\",";  not neccessary
		str += "\"startTime\":"+startTime+",";
		str += "\"endTime\":"+endTime;
		str += "}";
		return str;
	}
	
}
