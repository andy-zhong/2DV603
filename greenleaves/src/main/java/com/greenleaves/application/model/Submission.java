package com.greenleaves.application.model;

public class Submission {
	
	private int id;
	private int mid;
	private int sid;
	private int type;
	private String attachPath;
	private int submitTime;
	private int gradeTime;
	private SubmissionType typeObj;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMid() {
		return mid;
	}
	
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getAttachPath() {
		return attachPath;
	}
	
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	
	public int getSubmitTime() {
		return submitTime;
	}
	
	public void setSubmitTime(int submitTime) {
		this.submitTime = submitTime;
	}
	
	public int getGradeTime() {
		return gradeTime;
	}
	
	public void setGradeTime(int gradeTime) {
		this.gradeTime = gradeTime;
	}
	
	public SubmissionType getSubmissionType() {
		return typeObj;
	}
	
	public void setSubmissionType(SubmissionType typeObj) {
		this.typeObj = typeObj;
	}
	
	public String toString() {
		String str = "{";
		str += "\"id\":"+id+",";
		str += "\"sid\":"+sid+",";
		str += "\"mid\":"+mid+",";
		str += "\"type\":"+type+",";
		str += "\"attachPath\":\""+attachPath+"\",";
		str += "\"submitTime\":"+submitTime+",";
		str += "\"gradeTime\":"+gradeTime+",";
		str += "\"typeObj\":"+typeObj.toString();
		str += "}";
		return str;
	}

}
