package com.r2s.pte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findCategoryByParentCode(String id);

	@Query("SELECT c FROM Category c, QuestionGroup qg, Lesson l, LessonCategory lc, Question q "
			+ "WHERE q.questionGroup.id = qg.id AND qg.lesson.id = l.id AND l.id=lc.lesson.id AND lc.category.id=c.id AND"
			+ " q.id = :id")
	List<Category> getByQuestion(@Param("id") Long id);

	@Query("SELECT c FROM Category c, LessonCategory lc WHERE lc.category.id=c.id AND lc.lesson.id=:lessonId")
	Optional<Category> getByLesson(@Param("lessonId") Long id);

	boolean existsByCode(String code);

	Optional<Category> findByCode(String code);
}
