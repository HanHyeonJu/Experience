package com.project.experience.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.experience.entities.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByEmail(String email);

	Member findByName(String name);

	Member findNameByEmail(String email);

	
}
