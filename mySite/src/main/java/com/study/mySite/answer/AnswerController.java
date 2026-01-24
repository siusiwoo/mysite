package com.study.mySite.answer;


import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.study.mySite.question.Question;
import com.study.mySite.question.QuestionForm;
import com.study.mySite.question.QuestionService;
import com.study.mySite.user.SiteUser;
import com.study.mySite.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final UserService userService;
	private final QuestionService questionService;
	private final AnswerService answerService; 
//
//    AnswerController(AnswerService answerService) {
//        this.answerService = answerService;
//  } 
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String AnswerCreate(@Valid AnswerForm answerForm,BindingResult bindingResult,@PathVariable("id") Integer id,Model model,Principal principal) {
		//질문 저장
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		Question question = this.questionService.getQuestion(id);
		if(bindingResult.hasErrors()) {
			model.addAttribute(question);
			return "question_detail";
		}
		Answer answer = this.answerService.create(question,answerForm.getContent(), siteUser);

		 return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerFrom,@PathVariable("id") Integer id,Principal principal) {
		 Answer answer = this.answerService.getAnswer(id);
		 if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다."); 
		 }
		 
		 answerFrom.setContent(answer.getContent());
		 return "answer_form";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm,BindingResult bindingResult,@PathVariable("id") Integer id,Principal principal) {
		if(bindingResult.hasErrors()) {
			return "question_detail";
		}
		Answer answer = this.answerService.getAnswer(id);
		 if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다."); 
		 }
		this.answerService.modify(answer, answerForm.getContent());
		 
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String AnswerDelete(@PathVariable("id") Integer id,Principal principal) {
	    Answer answer = this.answerService.getAnswer(id);
	    
	    
	    this.answerService.delete(answer);
	    
	    return "redirect:/question/detail/"+answer.getQuestion().getId(); 
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal,@PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
	
}
