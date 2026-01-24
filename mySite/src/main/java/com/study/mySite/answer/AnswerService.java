package com.study.mySite.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.study.mySite.DataNotFoundException;
import com.study.mySite.question.Question;
import com.study.mySite.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	public Answer create(Question question, String content,SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		return answer;
	}
	
	public Answer getAnswer(Integer id) {
		 Optional<Answer> answer = this.answerRepository.findById(id);
		 
		 if(answer.isPresent()) {// isPresent 있으면 실행
			 return answer.get();
		 }else {
			 throw new DataNotFoundException("answer not found");
		 	}
		}
		// create() 메소드 -> 질문저장
		public void create(String content, SiteUser author) {
			Answer a = new Answer();
			
			a.setContent(content);
			a.setAuthor(author);
			a.setCreateDate(LocalDateTime.now());
			this.answerRepository.save(a);
		}
		public void modify(Answer answer,String content) {
			answer.setContent(content);
			answer.setModifyDate(LocalDateTime.now());
			this.answerRepository.save(answer);
			
		}
		
		public void delete(Answer answer) {
		    this.answerRepository.delete(answer);
		}
		
		public void vote(Answer answer, SiteUser siteUser) {
			if(answer.getVoter().contains(siteUser)) {
				answer.getVoter().remove(siteUser);
			}else {
				answer.getVoter().add(siteUser);
			}
			
			this.answerRepository.save(answer);
			}
	}

