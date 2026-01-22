package com.study.mySite.answer;


import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String questionCreate(Model model,@PathVariable("id") Integer id, 
			@Valid AnswerForm answerForm,BindingResult bindingResult, Principal principal) {
		
		Question question = this.questionService.getQuestion(id);
		 SiteUser siteUser =	this.userService.getUser(principal.getName()); 
		if (bindingResult.hasErrors()) {
	        model.addAttribute("question", question); 
	        return "question_detail"; 
	    }
		this.answerService.create(question, answerForm.getContent(),siteUser);
		//TODO: 답변을 저장
		return "redirect:/question/detail/"+id;
	}
	
	
}
