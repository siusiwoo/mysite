package com.study.mySite.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.study.mySite.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
	
	public Page<Question> getList(int page){
		Pageable pageable = PageRequest.of(page, 10);
		return this.questionRepository.findAll(pageable);
	}
	
	public Question getQuestion(Integer id) {
	 Optional<Question> question = this.questionRepository.findById(id);
	 
	 if(question.isPresent()) {// isPresent 있으면 실행
		 return question.get();
	 }else {
		 throw new DataNotFoundException("question not found");
	 	}
	}
	// create() 메소드 -> 질문저장
	public void create(String subject,String content) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q);
	}
}
