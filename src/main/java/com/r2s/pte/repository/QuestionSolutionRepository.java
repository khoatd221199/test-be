package com.r2s.pte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.QuestionSolution;
@Repository
public interface QuestionSolutionRepository extends JpaRepository<QuestionSolution, Long> {
	@Query(value = "SELECT qs FROM QuestionSolution qs Where qs.question.id = :id")
	List<QuestionSolution> getQuestionSolutionByQuestionId(@Param("id") long id);
	@Query(value = "SELECT COUNT(qs) FROM QuestionSolution qs WHERE qs.question.questionGroup.lesson.id = :lessonId")
	int countQuestionSoltutionByLessonId(@Param("lessonId") long lessonId);
	@Modifying
	@Query(value = "DELETE FROM QuestionSolution qs WHERE qs.question.id = :questionId")
	void deleteByQuestion(@Param("questionId") long questionId);
	Optional<QuestionSolution> findByValueText(String valueText);
}
