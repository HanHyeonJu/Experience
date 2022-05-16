package com.project.experience.model;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reply {
	
	/* 댓글 번호 */
	private int reply_no;
	
	/* 게시판 번호 */
	private int bno;
	
	private String writer;
	
	private String content;
	
	/* 등록 날짜 */
	@JsonFormat(pattern = "yyyy-MM-dd a hh:mm:ss")
	private LocalDateTime create_at;
	
	@JsonFormat(pattern = "yyyy-MM-dd a hh:mm:ss")
	private LocalDateTime update_at;
}
