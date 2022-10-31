package com.r2s.pte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.LessonCategory;
@Repository
public interface LessonCategoryRepository extends JpaRepository<LessonCategory, Long> {
	@Query(value = "FROM LessonCategory WHERE lesson.id = :id")
	List<LessonCategory> findByLessonId(@Param("id") long id);
	@Query(value = "SELECT COUNT (lc) FROM LessonCategory lc WHERE lc.lesson.id = :id")
	int countByLessonId(@Param("id") long id);
	@Modifying
	@Query(value = "DELETE FROM LessonCategory  q WHERE q.lesson.id = :id")
	void deleteByLessonId(@Param("id") long id);
	@Query(value = "FROM LessonCategory WHERE lesson.id = :id AND category.code=:code")
	Optional<LessonCategory> findByLessonIdAndCodeCategory(@Param("id") long id, @Param("code") String code);
}
