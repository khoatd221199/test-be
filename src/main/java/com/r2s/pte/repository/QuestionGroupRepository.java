package com.r2s.pte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.QuestionGroup;

@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {
	@Query(value = "SELECT qp FROM QuestionGroup qp Where qp.lesson.id = :id")
	Optional<QuestionGroup> findByLessonId(@Param("id") long id);
}
