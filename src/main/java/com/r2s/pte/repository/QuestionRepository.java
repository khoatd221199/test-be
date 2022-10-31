package com.r2s.pte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Question;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query(value = "SELECT q FROM Question q Where q.questionGroup.lesson.id = :id")
	List<Question> findQuestionByLessonId(@Param("id") long id);
	
	@Query(value = "SELECT q FROM Question q Where q.questionGroup.id = :id")
	List<Question>  findQuestionByQuestionGroupId(@Param("id") long id);
	@Query(value = "SELECT q.name FROM Question q WHERE q.id = :id")
	String getNameByQuestionId(Long id);
	@Modifying
	@Query(value = "DELETE FROM Question q WHERE q.questionGroup.id = :id")
	void deleteByQuestionGroup(@Param("id") long id);
	
}
