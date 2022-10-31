package com.r2s.pte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.r2s.pte.entity.DiscussionReactions;
@Repository
public interface DiscussionReactionsRepository extends JpaRepository<DiscussionReactions, Long> {
	List<DiscussionReactions> findAllByDiscussionId(long id);
	
	List<DiscussionReactions> findAllByCreatedBy(long id);

	DiscussionReactions findByDiscussionIdAndCreatedBy(long discussionId,long UserId);
	
	List<DiscussionReactions> removeByDiscussionIdAndCreatedBy(long discussionId,long UserId);

}
