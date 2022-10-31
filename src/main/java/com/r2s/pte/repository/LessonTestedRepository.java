package com.r2s.pte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.LessonTested;

@Repository
public interface LessonTestedRepository extends JpaRepository<LessonTested, Long> {
	@Query(value = "SELECT lt FROM LessonTested lt WHERE lt.lesson.id = :id")
	List<LessonTested> findByLessonId(@Param("id") Long id);
	@Query(value ="SELECT COUNT(lt) FROM LessonTested lt WHERE lt.lesson.id = :id")
	Long coutByLessonId(@Param("id") Long id);
	@Query(value = "SELECT lt FROM LessonTested lt WHERE lt.lesson.id = :idLesson and lt.userId = :idUser")
	List<LessonTested> getByLessonAndUser(@Param("idLesson") Long idLesson, @Param("idUser") Long idUser);
}
