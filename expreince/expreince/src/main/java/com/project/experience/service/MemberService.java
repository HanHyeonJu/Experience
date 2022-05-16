package com.project.experience.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.experience.dao.MemberRepository;
import com.project.experience.entities.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepo;
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepo.save(member);
	}

	private void validateDuplicateMember(Member member) {
		Member findEmail = memberRepo.findByEmail(member.getEmail());
		Member findName = memberRepo.findByName(member.getName());
		
		if(findEmail != null) {
			throw new IllegalStateException("이미 가입된 계정입니다");
		}
		if(findName != null) {
			throw new IllegalStateException("이미 존재하는 닉네임입니다");
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Member member = memberRepo.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
}
