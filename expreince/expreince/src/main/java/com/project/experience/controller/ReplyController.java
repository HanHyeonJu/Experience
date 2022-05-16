package com.project.experience.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.experience.model.Reply;
import com.project.experience.service.ReplyService;

@RestController
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	// 댓글추가	
	@PostMapping
	public Reply replyEnroll(@RequestBody Reply reply, HttpSession session, Principal principal) {
			
		replyService.enroll(reply);
		
		return reply;
	}
	
	// 댓글 리스트
	@GetMapping("/{bno}")
	public List<Reply> replyList(@PathVariable int bno){
		return replyService.replyList(bno);
	}
	
	// 댓글 수정
	@PutMapping
	public Reply replyModify(@RequestBody Reply reply) {
		replyService.modify(reply);
		reply = replyService.reply(reply.getReply_no());
		return reply;
	}
	
	// 댓글 삭제
	@DeleteMapping("/{id}")
	public void replyDelete(@PathVariable("id") int reply_no) {
		replyService.delete(reply_no);
	}
}
