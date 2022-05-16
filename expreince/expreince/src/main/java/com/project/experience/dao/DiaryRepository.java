package com.project.experience.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.experience.entities.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Integer>{
	
	Page<Diary> findAllByWriterOrderByIdDesc(String writer, Pageable pageable);
	
	@Query(value = "select * from diarys d where writer = :writer and d.title like %:keyword% or d.content like %:keyword% or d.create_day like %:keyword% or d.update_day like %:keyword%", nativeQuery = true)
	Page<Diary> findAllByKeywordAndWriterOrderByIdDesc(@Param("keyword") String keyword, String writer, Pageable pageable);
	
	@Query(value = "select count(id) from diarys d where writer = :writer and d.title like %:keyword% or d.content like %:keyword% or d.create_day like %:keyword% or d.update_day like %:keyword%", nativeQuery = true)
	long countByKeywordAndWriter(String keyword, String writer);

	@Query(value = "select count(*) from diarys where writer = :writer" )
	long count(String writer);	

}
