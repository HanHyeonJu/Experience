package com.project.experience.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.project.experience.model.Board;
import com.project.experience.model.Criteria;

@Mapper
public interface BoardMapper {

	public void enroll(Board board);

//	public List<Board> getList();
	
	/* 페이징 적용한 게시판 리스트 */
	public List<Board> getList(Criteria cri);
	
	/* 게시물 총 갯수(키워드로 검색했을 때 나오는 결과값도 구해야 하기 때문에 cri객체 넣어줌) */
	public int getTotal(Criteria cri);
	
	public Board getBoard(int bno);
	
	public int modify(Board board);
	
	public int delete(int bno);
	
	public int updateView(int bno);
}
