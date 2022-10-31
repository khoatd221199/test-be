package com.r2s.pte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	@Query("SELECT l FROM  Lesson l  WHERE l.id in (select  lc.lesson.id from LessonCategory lc where lc.category.id =:categoryId)")
	List<Lesson> getLessonByCategory(Long categoryId);

	@Query("SELECT l FROM Lesson l, LessonCategory lc WHERE l.id = lc.lesson.id AND l.zOrder = :order AND lc.category.id = :id")
	List<Lesson> findByCategoryAndZorder(@Param("order") Long order, @Param("id") Long id);

	Lesson findFirstByOrderByCreatedDateDesc();
	@Query("SELECT l FROM Lesson l, LessonCategory lc, QuestionGroup qg, Question q "
			+ "WHERE lc.lesson.id = l.id AND lc.lesson.questionGroup.id =qg.id AND q.questionGroup.id=qg.id AND q.id=:questionId ")
	Optional<Lesson> findByQuestionId(@Param("questionId")Long questionId);
	@Query("SELECT l.content FROM Lesson l, LessonCategory lc, QuestionGroup qg, Question q "
			+ "WHERE lc.lesson.id = l.id AND lc.lesson.questionGroup.id =qg.id AND q.questionGroup.id=qg.id AND q.id=:questionId ")
	String getContentByQuestionId(@Param("questionId")Long questionId);

}
