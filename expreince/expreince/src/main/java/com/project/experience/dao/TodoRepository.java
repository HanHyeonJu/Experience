package com.project.experience.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.experience.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

	Todo findByTitleAndIdNot(String title, int id);

	Page<Todo> findAllByWriterOrderByIdDesc(String writer, Pageable pageable);
	
	@Query(value = "select * from Todos t where writer = :writer and t.title like %:keyword% or t.stage like %:keyword% or t.start like %:keyword% or t.target like %:keyword%", nativeQuery = true)
	Page<Todo> findAllByKeywordAndWriterOrderByIdDesc(@Param("keyword") String keyword, String writer, Pageable pageable);
	
	@Query(value = "select count(id) from Todos t where writer = :writer and t.title like %:keyword% or t.stage like %:keyword% or t.start like %:keyword% or t.target like %:keyword%", nativeQuery = true)
	long countByKeywordAndWriter(String keyword, String writer);
	
	@Query(value = "select count(*) from todos where writer = :writer" )
	long count(String writer);
	
}
