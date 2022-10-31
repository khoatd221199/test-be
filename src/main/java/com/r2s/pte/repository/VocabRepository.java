package com.r2s.pte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Vocab;
@Repository
public interface VocabRepository extends JpaRepository<Vocab, Long>{
	boolean existsByVocab(String vocab);	
	@Query("SELECT v FROM Vocab v where v.vocab = :str")
	Vocab findByVocab(@Param("str")String vocab);
}
