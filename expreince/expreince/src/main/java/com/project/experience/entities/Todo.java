package com.project.experience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity(name = "todos")
@Table(name = "todos")
@Data // get,set,toString 자동 생성됨 - lombok
public class Todo {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		private String writer;
		
		@NotBlank(message="제목을 입력해주세요")
		@Size(max = 100, message = "100자 이하로 입력해주세요")
		private String title;
		
		@NotBlank(message="진행상황을 선택해주세요")
		private String stage; 
		
		@NotBlank(message="시작날짜를 입력해주세요")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name = "start")
		private String startDate;
		
		@NotBlank(message="마감날짜를 입력해주세요")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name = "target")
		private String targetDate;
		
		@Size(max = 200, message = "200자 이하로 입력해주세요")
		private String description;
		
}
