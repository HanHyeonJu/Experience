package com.project.experience.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.experience.dao.ReplyMapper;
import com.project.experience.model.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper replyMapper;
	
	
	@Override
	public void enroll(Reply reply) {
		replyMapper.enroll(reply);
	}

	@Override
	public List<Reply> replyList(int bno) {
		return replyMapper.replyList(bno);
	}
	
	@Override
	public int modify(Reply reply) {
		return replyMapper.modify(reply);
	}
	
	@Override
	public Reply reply(int reply_no) {
		return replyMapper.reply(reply_no);
	}
	
	@Override
	public int delete(int reply_no) {
		return replyMapper.delete(reply_no);
	}
}
