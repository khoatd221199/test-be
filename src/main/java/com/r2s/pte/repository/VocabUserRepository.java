package com.r2s.pte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.VocabUser;
@Repository
public interface VocabUserRepository extends JpaRepository<VocabUser, Long> {
	@Query("SELECT vu FROM VocabUser vu where vu.vocab.id = :vid and vu.userId =:uid")
	VocabUser findByVocabId(@Param("vid") long vocab, @Param("uid") long user);
	
	@Query("SELECT vu FROM VocabUser vu where vu.vocab.id = :vid and vu.userId =:uid and vu.category.id =:categoryid")
	VocabUser findByVocabIdAndCategoryId(@Param("vid") long vocab, @Param("uid") long user, @Param("categoryid") long category);
	
	Page<VocabUser> findAllByUserIdAndCategoryAndStatus(long user,Category category,boolean status, Pageable pageable);
	
	Page<VocabUser> findAllByUserId(long user, Pageable pageable);
	
	boolean existsByVocabId(long id);	

}
 