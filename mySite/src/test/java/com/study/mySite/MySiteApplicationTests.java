package com.study.mySite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.mySite.answer.Answer;
import com.study.mySite.answer.AnswerRepository;
import com.study.mySite.question.Question;
import com.study.mySite.question.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class MySiteApplicationTests {
	
		
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	//@Transactional //db가 끊기지않고 끝까지 db섹션이 유지되도록 하는 에노테이션 
	@Test
	void contextLoads() {
		/*
		 Question q1 = new Question();
		q1.setSubject("궁금합니다.");
		q1.setContent("질문 내용입니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("사랑합니다.");
		q2.setContent("질문 내용입니다.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
		
		List<Question> all = this.questionRepository.findAll();
		assertEquals(8, all.size());
		
		Question q = all.get(0);
		assertEquals("궁금합니다.", q.getSubject());
		
		
		 Optional<Question> op = this.questionRepository.findById(5);
		if (op.isPresent()) {
			Question q = op.get();
			assertEquals("질문 내용입니다.", q.getContent());
		}
		//에러부분
		this.questionRepository.deleteById(1);
		this.questionRepository.deleteById(2);

		Question q = this.questionRepository.findBySubjectAndContent("궁금합니다.","질문 내용입니다.");
		assertEquals(5,q.getId());
		//
		List<Question> qList = this.questionRepository.findBySubjectLike("%니다%");
		Question q = qList.get(0);
		System.out.println(q.getSubject());
		
		Optional<Question> q = this.questionRepository.findById(5);
		assertTrue(q.isPresent());
		Question question = q.get();
		question.setSubject("수정된 제목");
		this.questionRepository.save(question);
	
		
		
				*/
		
		
		/*
		 문제1
		Optional<Question> q = this.questionRepository.findById(5);
		
		assertTrue(q.isPresent());
		Question question = q.get();
		
		 Answer q3 = new Answer();
			q3.setContent("진짜입니다.");
			q3.setCreateDate(LocalDateTime.now());
			q3.setQuestion(question);
			this.answerRepository.save(q3);
		문제2	
		for(int i = 0; i < 50;i++) {
			 Question q1 = new Question();
				q1.setSubject("테스트 코드를 이용해 생성한 내용:[내용"+i+"]");
				q1.setContent("테스트 코드를 이용해 생성한 내용:[제목"+i+"]");
				q1.setCreateDate(LocalDateTime.now());
				this.questionRepository.save(q1);
		}
		*/
		/*
		Optional<Question> q = this.questionRepository.findById(5);
		
		assertTrue(q.isPresent());
		Question question = q.get();
		
		 Answer q3 = new Answer();
			q3.setContent(question.getContent());
			q3.setCreateDate(LocalDateTime.now());
			q3.setQuestion(question);
			this.answerRepository.save(q3);
	
		 Optional<Question> op = this.questionRepository.findById(5);
		 assertTrue(op.isPresent());
		 Question q = op.get();
		 List<Answer> answerList = q.getAnswerList();
		 System.out.println(answerList.get(0).getContent());
		 System.out.println(answerList.size());
			*/
		for(int i = 1; i <= 250;i++) {
			 Question q1 = new Question();
				q1.setSubject("테스트 코드를 이용해 생성한 내용:[내용"+i+"]");
				q1.setContent("테스트 코드를 이용해 생성한 내용:[제목"+i+"]");
				q1.setCreateDate(LocalDateTime.now());
				this.questionRepository.save(q1);
		}
	}

}
