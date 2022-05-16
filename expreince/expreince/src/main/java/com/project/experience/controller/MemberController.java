package com.project.experience.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.experience.dto.MemberDto;
import com.project.experience.entities.Member;
import com.project.experience.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/register")
	public String registerView(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "login/register";
	}
	
	@PostMapping("/register")
	public String register(@Valid MemberDto memberDto, BindingResult bindingResult, 
							Model model, RedirectAttributes attr) {
		if(bindingResult.hasErrors()) {
			return "login/register";
		}
		
		if(!memberDto.getPassword().equals(memberDto.getConfirmPassword())) {
			model.addAttribute("passwordNotMatch", "패스워드 확인이 틀림");
			return "login/register";
		}
		
		try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            attr.addFlashAttribute("message", "회원가입에 성공했습니다");
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "login/register";
        }
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginView() {
		return "login/login";
	}
	
	@GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "존재하지 않는 아이디 또는 비밀번호입니다");
        return "login/login";
    }
}
