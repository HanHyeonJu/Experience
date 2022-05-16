package com.project.experience.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.project.experience.model.Board;

import lombok.extern.java.Log;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback
@Log
public class BoardMapperTest {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void test() {
		/*게시판 등록 테스트*/
//		Board board = new Board();
//		
//		board.setTitle("제목테스트");
//		board.setContent("내용테스트");
//		board.setWriter("글쓴이테스트");
//		
//		boardMapper.enroll(board);
		
//		List<Board> list = boardMapper.getList();
//		/* for each문, 람다식 */
//		list.forEach(board -> log.info("" + board));
	}
	
}
