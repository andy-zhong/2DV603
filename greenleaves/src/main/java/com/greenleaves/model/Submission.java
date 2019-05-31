package com.greenleaves.model;

public class Submission {
	
	private int id;
	private int mid;
	private int sid;
	private int type;
	private String attachPath;
	private int submitTime;
	private int gradeTime;
	private int score;
	
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
