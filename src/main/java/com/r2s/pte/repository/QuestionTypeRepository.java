package com.r2s.pte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.QuestionType;
@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {

}
