package com.project.experience.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

	private int bno;
	
	@NotBlank(message = "제목을 입력해주세요")
	@Size(max = 100, message = "100자 이하로 입력해주세요")
	private String title;
	
	@NotBlank(message = "내용을 입력해주세요")
	private String content;
	
	private String writer;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	
	private int view;
}
