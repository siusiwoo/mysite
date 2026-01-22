package com.study.mySite.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.study.mySite.answer.AnswerController;
import com.study.mySite.answer.AnswerForm;
import com.study.mySite.user.SiteUser;
import com.study.mySite.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 생성자를 자동으로 만들어주는 어노테이션
@Controller
@RequestMapping("/question")// 주고건 거치고 가겠다 하면 이렇게 할수도있음
public class QuestionController {

    private final AnswerController answerController;
	private final QuestionService questionService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model,@RequestParam(value="page",defaultValue="0") int page){
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model,@PathVariable("id") Integer id,AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	@PreAuthorize("isAuthenticated()")//로그인 페이지로 자동으로 이동
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	//목록 페이지로 이동
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm,BindingResult bindingResult,Model model,Principal principal) {
		//질문 저장
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(),questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionFrom,@PathVariable("id") Integer id,Principal principal) {
		 Question question = this.questionService.getQuestion(id);
		 if(!question.getAuthor().getUsername().equals(principal.getName())) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다."); 
		 }
		 questionFrom.setSubject(question.getSubject());
		 questionFrom.setContent(question.getContent());
		 
		 return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionFrom,BindingResult bindingResult,@PathVariable("id") Integer id,Principal principal) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		 Question question = this.questionService.getQuestion(id);
		 if(!question.getAuthor().getUsername().equals(principal.getName())) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다."); 
		 }
		this.questionService.modify(question, questionFrom.getSubject(), questionFrom.getContent());
		 
		 return String.format("redirect:/question/detail/%s",id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id,Principal principal) {
	    Question question = this.questionService.getQuestion(id);
	    
	    
	    this.questionService.delete(question);
	    
	    return "redirect:/question/list"; 
	}
}
