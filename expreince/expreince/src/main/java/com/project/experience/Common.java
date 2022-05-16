package com.project.experience;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.experience.dao.MemberRepository;
import com.project.experience.entities.Member;

@ControllerAdvice
public class Common {

	@Autowired
	private MemberRepository memberRepo;
	
	@ModelAttribute
	public void sharedData(Model model, Principal principal) {
		if(principal != null) {
			Member member = memberRepo.findNameByEmail(principal.getName());
			model.addAttribute("principal", member.getName());
		}
	}
}
