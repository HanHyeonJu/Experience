package com.project.experience.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.experience.dao.MemberRepository;
import com.project.experience.entities.Member;
import com.project.experience.model.Board;
import com.project.experience.model.Criteria;
import com.project.experience.model.PageMaker;
import com.project.experience.model.Reply;
import com.project.experience.service.BoardService;
import com.project.experience.service.ReplyService;

@Controller
@RequestMapping("/boards")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@GetMapping
	public String boardList(Criteria cri, Model model) {
		model.addAttribute("boardList", boardService.getList(cri));
		
		int total = boardService.getTotal(cri);
		PageMaker pmk = new PageMaker(cri, total);
		model.addAttribute("pmk", pmk);
		
		return "boards/board-list";
	}
	
	
	@GetMapping("/enroll")
	public String boardEnroll(@ModelAttribute Board board) {
		return "boards/board-new";
	}
	
	@PostMapping("/enroll")
	public String boardEnroll(@Valid Board board, BindingResult bindingResult, RedirectAttributes attr, 
							  HttpSession session, Principal principal) {
		if(bindingResult.hasErrors()) return "boards/board-new";
		
		Member member = memberRepo.findNameByEmail(principal.getName());
		System.out.println(member);
		board.setWriter(member.getName());
		
		boardService.enroll(board);
		attr.addFlashAttribute("message", "게시글이 등록되었습니다");
		return "redirect:/boards";
	}
	
	@GetMapping("/board")
	public String getBoard(@RequestParam("bno") int bno, Model model, Criteria cri, Principal principal) {
		boardService.updateView(bno);
		
		model.addAttribute("board", boardService.getBoard(bno));
		model.addAttribute("cri", cri);
		
		// 로그인을 하지 않았을 때도 board페이지에서 댓글리스트를 볼 수 있도록 함
		if(principal == null) {
			List<Reply> replyList = replyService.replyList(bno);
			model.addAttribute("replyList", replyList);
		}
		
		return "boards/board";
	}
	
	@GetMapping("/modify")
	public String modifyBoard(@RequestParam("bno") int bno, Model model, Criteria cri) {
		model.addAttribute("board", boardService.getBoard(bno));
		model.addAttribute("cri", cri);
		return "boards/board-edit";
	}
	
	@PostMapping("/modify")
	public String modifyBoard(@Valid Board board, BindingResult bindingResult, RedirectAttributes attr) {
		if(bindingResult.hasErrors()) return "boards/board-edit";
		
		boardService.modify(board);
		attr.addFlashAttribute("message", "게시글이 수정되었습니다");
		return "redirect:/boards/board?bno=" + board.getBno();
	}
	
	@GetMapping("/delete")
	public String deleteBoard(@RequestParam("bno") int bno, RedirectAttributes attr) {
		boardService.delete(bno);
		attr.addFlashAttribute("message", "게시글이 삭제되었습니다");
		return "redirect:/boards";
	}
}
