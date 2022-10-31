package com.r2s.pte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.LessonMedia;

@Repository
public interface LessonMediaRepository extends JpaRepository<LessonMedia, Long> {
	@Query(value = "SELECT lm FROM LessonMedia lm WHERE lm.lesson.id = :lessonId")
	List<LessonMedia> findByLessonId(@Param("lessonId") Long lessonId);
}
