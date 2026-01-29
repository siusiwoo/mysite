package com.study.mySite.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question,Integer>{
	Question findBySubject(String subject);
	Question findByContent(String content);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec,Pageable pageable);
	
	@Query("select distinct q\r\n"
			+ "    from Question q\r\n"
			+ "    left join q.author u1\r\n"
			+ "    left join q.answerList a\r\n"
			+ "    left join a.author u2\r\n"
			+ "    where\r\n"
			+ "        q.subject like concat('%', :kw, '%')\r\n"
			+ "        or q.content like concat('%', :kw, '%')\r\n"
			+ "        or u1.username like concat('%', :kw, '%')\r\n"
			+ "        or a.content like concat('%', :kw, '%')\r\n"
			+ "        or u2.username like concat('%', :kw, '%')"
			)
	Page<Question> findAllBykeyword(@Param("kw") String kw,Pageable pageable);
}
