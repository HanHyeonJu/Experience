package com.project.experience.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.experience.dao.DiaryRepository;
import com.project.experience.dao.MemberRepository;
import com.project.experience.entities.Diary;
import com.project.experience.entities.Member;

@Controller
@RequestMapping("/diarys")
public class DiaryController {

	@Autowired
	private DiaryRepository diaryRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@GetMapping
	public String diaryList(Model model, @RequestParam(value="page", defaultValue="0") int page, 
							String keyword, RedirectAttributes attr, Principal principal) {
		
		Member member = memberRepo.findNameByEmail(principal.getName());
		String writer = member.getName();
		
		int perPage = 3; 
		Pageable pageable = PageRequest.of(page, perPage);
	
		if(keyword != null) {
			Page<Diary> diarys = diaryRepo.findAllByKeywordAndWriterOrderByIdDesc(keyword, writer, pageable);
			model.addAttribute("diarys", diarys);
			
			long count = diaryRepo.countByKeywordAndWriter(keyword, writer);
			double pageCount = Math.ceil((double)count/(double)perPage);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("count", count);
	
			if(diarys.getTotalElements() < 1) {
				attr.addFlashAttribute("message", "??????????????? ????????????.");
				return "redirect:/diarys";
			}
			} else {
				Page<Diary> diarys = diaryRepo.findAllByWriterOrderByIdDesc(writer, pageable);
				model.addAttribute("diarys", diarys);
				long count = diaryRepo.count(writer);
				double pageCount = Math.ceil((double)count/(double)perPage);
				model.addAttribute("pageCount", pageCount);
				model.addAttribute("count", count);
			}
			
			model.addAttribute("perPage", perPage);
			model.addAttribute("page", page);
		
			return "diarys/diary-list";
	}
	
	@GetMapping("/new")
	public String diaryAdd(@ModelAttribute Diary diary) {
		return "diarys/diary-new";
	}
	
	@PostMapping("/new")
	public String diaryAdd(@Valid Diary diary, BindingResult bindingResult, RedirectAttributes attr, 
							MultipartFile file, HttpSession session, Principal principal) throws IOException {
		if(bindingResult.hasErrors()) return "diarys/diary-new";
		
		Member member = memberRepo.findNameByEmail(principal.getName());
		//System.out.println(member);
		diary.setWriter(member.getName());
		
		boolean fileOk = false;
		
		
		byte[] bytes = file.getBytes(); // ???????????? ????????? ????????? ?????????
		String originalFileName = file.getOriginalFilename(); // ????????? ??????
		
		// ????????? ????????? ?????? ?????? 
		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString() + "_" + originalFileName;
		
		// ????????? experience???????????? ???????????? ??????
		Path path = Paths.get("src/main/resources/static/diary_file/"+ fileName); //????????? ????????? ????????? ????????????
		
		if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
			fileOk = true; // ???????????? .jpg ?????? .png??? ???????????? OK
		}
		
		attr.addFlashAttribute("message", "??????????????? ?????????????????????");
		
		if(!fileOk) {
			attr.addFlashAttribute("message","???????????? ????????? ???????????? jpg, png??? ????????????");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("diary", diary);
		} 
		else {
			diary.setImage(fileName);
			diaryRepo.save(diary);
			
		
			Files.write(path, bytes); // ?
			return "redirect:/diarys";
		} 

		return "redirect:/diarys/new";
	}
	
	@GetMapping("/diary")
	public String getDiary(@RequestParam("id") int id, Model model) {
		Diary diary = diaryRepo.getById(id);
		model.addAttribute("diary", diary);
		return "diarys/diary";
	}
	
	@GetMapping("/edit/{id}")
	public String editDiary(@PathVariable int id, Model model) {
		Diary diary = diaryRepo.getById(id);
		model.addAttribute("diary", diary);
		return "diarys/diary-edit";
	}

	@PostMapping("/edit")
	public String editDiary(@Valid Diary diary, BindingResult bindingResult, RedirectAttributes attr,
							MultipartFile file, Principal principal) throws IOException {
		
		Diary currentDiary = diaryRepo.getById(diary.getId());
		
		if(bindingResult.hasErrors()) return "diarys/diary-edit";
		
		Member member = memberRepo.findNameByEmail(principal.getName());
		diary.setWriter(member.getName());
		
		boolean fileOk = false;
		byte[] bytes = file.getBytes(); // ???????????? ????????? ????????? ?????????
		String fileName = file.getOriginalFilename(); // ????????? ??????
		Path path = Paths.get("src/main/resources/static/diary_file/"+ fileName); //????????? ????????? ????????? ????????????
		
		if(!file.isEmpty()) { // ??? ????????? ????????? ?????????
			if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
				fileOk = true; // ???????????? .jpg ?????? .png??? ???????????? OK
				UUID uuid = UUID.randomUUID();
				fileName = uuid.toString() + "_" + fileName;
				}
		} 
		else {
				fileOk = true; 
		}
				
		// ??????????????? ?????????
		attr.addFlashAttribute("message","??????????????? ?????????????????????");
		
		if(!fileOk) {
			attr.addFlashAttribute("message","???????????? jpg??? png??? ??????????????????");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("diary", diary); // ????????? ???????????? ?????? ??? ????????? ?????? ??? ?????????
		} else {
			
			if (!file.isEmpty()) { // ????????? ????????? ????????? ?????? ???????????? ??????(?????? ?????? ?????? ??????)
				Path currentPath = Paths.get("src/main/resources/static/diary_file/"+ currentDiary.getImage());
				Files.delete(currentPath);
				diary.setImage(fileName);
				
				path = Paths.get("src/main/resources/static/diary_file/"+ fileName);
				Files.write(path, bytes);	//Files ???????????? ????????? ????????? ??????			
			} else {
				diary.setImage(currentDiary.getImage());
			}
			
			diaryRepo.save(diary);
		}
		
		return "redirect:/diarys/diary?id=" + diary.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String deleteDiary(@PathVariable int id, RedirectAttributes attr) {
		
		diaryRepo.deleteById(id);
		attr.addFlashAttribute("message", "??????????????? ?????? ???????????????.");
		attr.addFlashAttribute("alertClass", "alert-success");	
		
		return "redirect:/diarys";
	}
}
