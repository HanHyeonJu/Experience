package com.project.experience.service;

import java.util.List;

import com.project.experience.model.Board;
import com.project.experience.model.Criteria;

public interface BoardService {

	public void enroll(Board board);

	public List<Board> getList(Criteria cri);
	
	public int getTotal(Criteria cri);

	public Board getBoard(int bno);
	
	public int modify(Board board);
	
	public int delete(int bno);
	
	public int updateView(int bno);

}
