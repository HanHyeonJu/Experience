package com.project.experience.dto;



import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	@NotBlank(message = "이름을 입력해주세요")
	private String name;
	
	@NotEmpty(message = "이메일을 입력해주세요")
	@Email(message = "이메일 형식으로 입력해주세요")
	private String email;
	
	@NotEmpty(message = "비밀번호를 입력해주세요")
	@Pattern(regexp= "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*()_=+]).{8,20}$",
			 message = "비밀번호는 8자 이상, 20자 이하로 영어 대,소문자, 숫자, 특수문자를 포함하여 입력해주세요")
	private String password;
	
	@Transient
	private String confirmPassword;
}
