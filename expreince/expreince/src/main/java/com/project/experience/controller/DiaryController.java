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
				attr.addFlashAttribute("message", "검색결과가 없습니다.");
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
		
		
		byte[] bytes = file.getBytes(); // 업로드된 이미지 파일의 데이터
		String originalFileName = file.getOriginalFilename(); // 파일의 이름
		
		// 이미지 파일명 중복 방지 
		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString() + "_" + originalFileName;
		
		// 주소는 experience프로젝트 안에서의 기준
		Path path = Paths.get("src/main/resources/static/diary_file/"+ fileName); //저장할 파일의 위치와 이름까지
		
		if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
			fileOk = true; // 확장자가 .jpg 또는 .png인 경우에만 OK
		}
		
		attr.addFlashAttribute("message", "성공적으로 저장되었습니다");
		
		if(!fileOk) {
			attr.addFlashAttribute("message","이미지가 없거나 확장자가 jpg, png가 아닙니다");
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
		byte[] bytes = file.getBytes(); // 업로드된 이미지 파일의 데이터
		String fileName = file.getOriginalFilename(); // 파일의 이름
		Path path = Paths.get("src/main/resources/static/diary_file/"+ fileName); //저장할 파일의 위치와 이름까지
		
		if(!file.isEmpty()) { // 새 이미지 파일이 있으면
			if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
				fileOk = true; // 확장자가 .jpg 또는 .png인 경우에만 OK
				UUID uuid = UUID.randomUUID();
				fileName = uuid.toString() + "_" + fileName;
				}
		} 
		else {
				fileOk = true; 
		}
				
		// 성공적으로 추가됨
		attr.addFlashAttribute("message","성공적으로 수정되었습니다");
		
		if(!fileOk) {
			attr.addFlashAttribute("message","이미지는 jpg나 png만 사용해주세요");
			attr.addFlashAttribute("alertClass","alert-danger");
			attr.addFlashAttribute("diary", diary); // 에러가 나더라도 적은 거 그대로 보일 수 있게함
		} else {
			
			if (!file.isEmpty()) { // 수정할 이미지 파일이 있을 경우에만 저장(이때 이전 파일 삭제)
				Path currentPath = Paths.get("src/main/resources/static/diary_file/"+ currentDiary.getImage());
				Files.delete(currentPath);
				diary.setImage(fileName);
				
				path = Paths.get("src/main/resources/static/diary_file/"+ fileName);
				Files.write(path, bytes);	//Files 클래스를 사용해 파일을 저장			
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
		attr.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");	
		
		return "redirect:/diarys";
	}
}
