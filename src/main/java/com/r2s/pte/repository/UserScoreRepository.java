package com.r2s.pte.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.UserScore;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
	@Query(value = "SELECT u FROM UserScore u JOIN FETCH u.question  WHERE u.question.questionGroup.lesson.id = :lessonId AND u.user= :userId")
	List<UserScore> getByLessonAndUser(@Param("lessonId") Long lessonId, @Param("userId") Long userId);
	
	@Query(value = "SELECT u FROM UserScore u JOIN FETCH u.question  WHERE u.question.questionGroup.lesson.id = :lessonId ")
	List<UserScore> getByLesson(@Param("lessonId") Long lessonId);


	@Query(value = "SELECT u FROM UserScore u WHERE u.question.id IN (:questionIds) AND u.user=:userId")
	List<UserScore> getByQuestionAndUser(@Param("questionIds") List<Long> questionIds, @Param("userId") Long userId);

	@Query(value = "SELECT u FROM UserScore u WHERE u.question.id IN (:questionIds)")
	List<UserScore> getByQuestion(@Param("questionIds") List<Long> questionIds);

	@Query(value = "SELECT u FROM UserScore u WHERE u.question.id = :questionId")
	List<UserScore> getByQuestionId(@Param("questionId") Long questionId);

	@Modifying
	@Query(value = "DELETE FROM UserScore u WHERE u.createdDate = :dateTime")
	void deleteByCreatedDateEquals(LocalDateTime dateTime);
	@Query(value = "SELECT u.id FROM UserScore u WHERE u.createdDate = :dateTime")
	List<Long> getIdByCreateDateEquals(LocalDateTime dateTime);
}
