package com.project.experience.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.experience.dao.MemberRepository;
import com.project.experience.dao.TodoRepository;
import com.project.experience.entities.Member;
import com.project.experience.entities.Todo;

@Controller
@RequestMapping("/todos")
public class TodoController {

	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private MemberRepository memberRepo;
		
	@GetMapping
	public String todoList(Model model, @RequestParam(value="page", defaultValue="0") int page,
						   String keyword, RedirectAttributes attr, Principal principal) {
		
		Member member = memberRepo.findNameByEmail(principal.getName());
		String writer = member.getName();
		
		int perPage = 7; 
		Pageable pageable = PageRequest.of(page, perPage);

		if(keyword != null) {
			Page<Todo> todoList = todoRepo.findAllByKeywordAndWriterOrderByIdDesc(keyword, writer, pageable);
			
			long count = todoRepo.countByKeywordAndWriter(keyword, writer);
			double pageCount = Math.ceil((double)count/(double)perPage);
			
			model.addAttribute("todoList",todoList);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("count", count);
			if(todoList.getTotalElements() < 1) {
				attr.addFlashAttribute("message", "존재하지 않는 todo입니다");
				return "redirect:/todos";
			}
		} else {
			Page<Todo> todoList = todoRepo.findAllByWriterOrderByIdDesc(writer, pageable);
			model.addAttribute("todoList",todoList);
			long count = todoRepo.count(writer);
			double pageCount = Math.ceil((double)count/(double)perPage);
			
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("count", count);
			
		}
		
		model.addAttribute("perPage", perPage);
		model.addAttribute("page", page);
		

		return "todos/todo-list";
	}
	
	@GetMapping("/todo")
	public String getTodo(@RequestParam("id") int id, Model model) {
		Todo todo = todoRepo.getById(id);
		model.addAttribute("todo", todo);
		return "todos/todo";
	}
	
	@GetMapping("/new")
	public String create(@ModelAttribute Todo todo) {
		return "todos/todo-new";
	}
	
	@PostMapping("/new")
	public String create(@Valid Todo todo, BindingResult bindingResult, RedirectAttributes attr, Principal principal) {
		
		if(bindingResult.hasErrors()) return "todos/todo-new";
		
		Member member = memberRepo.findNameByEmail(principal.getName());
		todo.setWriter(member.getName());
		
		attr.addFlashAttribute("message", "성공적으로 저장되었습니다");
		attr.addFlashAttribute("alertClass", "alert-success"); 
		
		Todo todoExist = todoRepo.findByTitleAndIdNot(todo.getTitle(), todo.getId());
		
		if(todoExist != null) {
			attr.addFlashAttribute("message","존재하는 todo입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("todo", todo);
		} else {
			todoRepo.save(todo);
		}
	
		return "redirect:/todos/new"; 
	}
	
	@GetMapping("/edit/{id}")
	public String editTodo(@PathVariable int id, Model model) {
		Todo todo = todoRepo.getById(id);
		model.addAttribute("todo", todo);
		return "todos/todo-edit";
	}
	
	@PostMapping("/edit")
	public String editTodo(@Valid Todo todo, BindingResult bindingResult, RedirectAttributes attr) {
		
		if(bindingResult.hasErrors()) return "todos/todo-edit"; 
		
		attr.addFlashAttribute("message", "성공적으로 수정되었습니다");
		
		Todo todoExist = todoRepo.findByTitleAndIdNot(todo.getTitle(), todo.getId());
		
		if(todoExist != null) {
			attr.addFlashAttribute("message","존재하는 todo입니다");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("todo", todo);
		} else {
			todoRepo.save(todo);
		}
		
		return "redirect:/todos";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTodo(@PathVariable int id, RedirectAttributes attr) {
		
		todoRepo.deleteById(id);
		attr.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");	
		
		return "redirect:/todos";
	}
	
}
