package com.r2s.pte.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Discussion;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

	Page<Discussion> findAllByLessonIdAndParentIdAndIsIncludeAnswer(long lessson, Long parentId, Pageable pageable,Boolean isIncludeAnswer);

	List<Discussion> findAllByParentId(long id);

	@Query(value = "SELECT d FROM Discussion d WHERE d.lesson.id = :lessonId")
	List<Discussion> findByLessonId(@Param("lessonId") Long lessonId);
	
	Discussion findByCreatedDateAndCreatedByAndLessonIdAndIsIncludeAnswer(LocalDateTime createdDate,Long userId,Long lessonId,Boolean isIncludeAnswer);
}
