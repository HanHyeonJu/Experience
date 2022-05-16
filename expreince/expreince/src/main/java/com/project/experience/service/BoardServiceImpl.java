package com.project.experience.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.experience.dao.BoardMapper;
import com.project.experience.model.Board;
import com.project.experience.model.Criteria;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper boardMapper;
	
	
	@Override
	public void enroll(Board board) {
		boardMapper.enroll(board);	
	}

	@Override
	public List<Board> getList(Criteria cri) {
		return boardMapper.getList(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotal(cri);
	}
	
	@Override
	public Board getBoard(int bno) {
		return boardMapper.getBoard(bno);
	}
	
	@Override
	public int modify(Board board) {
		return boardMapper.modify(board);
	}
	
	@Override
	public int delete(int bno) {
		return boardMapper.delete(bno);
	}
	
	@Override
	public int updateView(int bno) {
		return boardMapper.updateView(bno);
	}
}
