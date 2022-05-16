package com.project.experience.service;

import java.util.List;

import com.project.experience.model.Reply;

public interface ReplyService {

	public void enroll(Reply reply);
	
	public List<Reply> replyList(int bno);
	
	public int modify(Reply reply);
	
	public Reply reply(int reply_no);
	
	public int delete(int reply_no);
}
