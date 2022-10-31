package com.r2s.pte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.QuestionOption;
@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
	@Query(value = "SELECT qo FROM QuestionOption qo Where qo.question.id = :id")
	List<QuestionOption> findQuestionOptionByQuestionId(@Param("id") long id);
	@Modifying
	@Query(value = "DELETE FROM QuestionOption qo WHERE qo.question.id = :questionId")
	void deleteQuestionOptionByQuestion(@Param("questionId") long questionId);
	
	
}
