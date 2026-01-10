package com.study.mySite.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 생성자를 자동으로 만들어주는 어노테이션
@Controller
@RequestMapping("/question")// 주고건 거치고 가겠다 하면 이렇게 할수도있음
public class QuestionController {
	private final QuestionService questionService;
	
	@GetMapping("/list")
	
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model,@PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate() {
		return "question_form";
	}
	//목록 페이지로 이동
//	@PostMapping("/create")
//	public String questionCreate() {
//		//질문 저장
//	}
}
