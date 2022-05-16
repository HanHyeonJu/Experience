package com.project.experience.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.experience.model.Reply;

@Mapper
public interface ReplyMapper {
	
	/* 댓글 등록 */
	public void enroll(Reply reply);
	
	/* 댓글 목록(보드글 번호 필요) */
	public List<Reply> replyList(int bno);
	
    /* 댓글 하나만 가져오기 */	
	public Reply reply(int reply_no);
	
	/* 댓글 수정 */
	public int modify(Reply reply);
	
	/* 댓글 삭제 */
	public int delete(int reply_no);
	
}
