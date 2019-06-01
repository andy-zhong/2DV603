package com.greenleaves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.dao.FeedbackDao;
import com.greenleaves.dao.SubmissionDao;
import com.greenleaves.model.Feedback;
import com.greenleaves.model.Member;
import com.greenleaves.model.Submission;
import com.greenleaves.model.SubmissionType;

@Repository
@Component
public class FeedbackService {

	@Autowired
	FeedbackDao fd;
	@Autowired
	SubmissionService ss;
	@Autowired
	SubmissionDao sd;
	
	
	public List<Feedback> getListBySubmissionId(int id) {
		List<Feedback> list = fd.feedbackFind("select * from gl_feedback where `sid` = ?", new Object[] {id});
		return list;
	}
	
	
	public String submit(Member member, int id, int score, String content) {
		Submission submission = ss.readById(id);
		if(submission==null) return "No submission";
		if(submission.getMid()==member.getId()) return "You cannot review on your own submission";
		SubmissionType st = ss.readSubmissionTypeById(submission.getType());
		// Check Permission
		// Just coordinator can feedback description
		if(
			st.getType().equalsIgnoreCase("description") && 
			!member.getGroupObj().getType().equalsIgnoreCase("coordinator")
		) {
			return "Access denied";
		}
		// Coordinator can feedback all, supervisor just can feedback their own students' submissions
		if(
			st.getType().equalsIgnoreCase("plan") && 
			!member.getGroupObj().getType().equalsIgnoreCase("coordinator") && (
			!member.getGroupObj().getType().equalsIgnoreCase("supervisor") || 
			(member.getGroupObj().getType().equalsIgnoreCase("supervisor") && submission.getSid()!=member.getId()))
		) {
			return "Access denied";
		}
		// Check if have submitted
		if(checkSubmitted(id, member.getId())) return "You have submitted feedback of this submission";
		// Check score
		if(score<1 || score>6) return "Please select valid score";
		// Insert
		fd.feedbackCreate(member.getId(), id, content, score);
		// Update submission score if coordinator
		if(member.getGroupObj().getType().equalsIgnoreCase("coordinator")) {
			sd.submissionUpdate(new Object[] {"score", "gradeTime"}, new Object[] {score, System.currentTimeMillis()/1000, id});
		} 
		return "OK";
	}
	
	
	public boolean checkSubmitted(int sid, int mid) {
		Feedback fb = fd.feedbackFindOne("select id from gl_feedback where `sid` = ? and `mid` = ?", new Object[] {sid, mid});
		return fb==null ? false : true;
	}
	
}
