package com.study.mySite.qnswer;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.mySite.question.Question;
import com.study.mySite.question.QuestionService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
	private final QuestionService questionService;

//    AnswerController(AnswerService answerService) {
//        this.answerService = answerService;
//    } 
	
	@PostMapping("/create/{id}")
	public String createAbswer(Model model,@PathVariable("id") Integer id,@RequestParam(value="content") String content) {
		
		Question question = this.questionService.getQuestion(id);
		this.answerService.create(question, content);
		//TODO: 답변을 저장
		return "redirect:/question/detail/"+id;
	}
	
	
}
