package com.r2s.pte.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.QuestionResponseUser;

@Repository
public interface QuestionResponseUserRepository extends JpaRepository<QuestionResponseUser, Long> {
	@Query(value = "SELECT qru.valueText FROM UserScore u INNER JOIN QuestionResponseUser qru ON  u.questionResponseUser.id = qru.id "
			+ "WHERE u.id IN (:listId)")
	List<String> getValueTextByUserScore(@Param("listId") List<Long> userScoreId);

	@Query(value = "SELECT qru FROM QuestionResponseUser qru WHERE qru.question.id = :questionId AND qru.userId=:userId AND qru.createdDate=:createdDate")
	List<QuestionResponseUser> findByQuestionId(@Param("questionId") Long questionId,@Param("userId") Long userId, @Param("createdDate") LocalDateTime createDate);
	
	@Query(value = "SELECT qru FROM QuestionResponseUser qru WHERE qru.question.id = :questionId")
	List<QuestionResponseUser> findByQuestion(@Param("questionId") Long questionId);

}
