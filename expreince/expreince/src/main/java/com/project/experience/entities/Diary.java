package com.project.experience.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity(name = "diarys") // count 찾을 때 not mapped 에러 해결 위함
@Table(name = "diarys")
@Data
public class Diary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "제목을 입력해주세요")
	@Size(max = 100, message = "100자 이하로 입력해주세요")
	private String title;
	
	@NotBlank(message = "내용을 입력해주세요")
	private String content;
	
	@Column(name="create_day", updatable=false)
	@CreationTimestamp
	private LocalDateTime createDay;
	
	@Column(name="update_day")
	@UpdateTimestamp
	private LocalDateTime updateDay;
	
	
	private String image;
	
	private String writer;
}
