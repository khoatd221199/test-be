package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.dto.DiscussionReactionsCreateDTO;
import com.r2s.pte.service.DiscussionReactionsService;

@RestController
@RequestMapping(value = {"api-dev/discussion_reactions","api-pro/discussion_reactions","api/discussion_reactions"})
public class DiscussionReactionsController {
	@Autowired
	private DiscussionReactionsService discussionReactionsService;

	@Autowired
	private MessageSource messageSource;
	private String messageResponse = "";
	
	
	@GetMapping("discussion/{id}")
	public ResponseEntity<?> findAllByDiscussion(@PathVariable long id)
	{
		return ResponseEntity.ok(this.discussionReactionsService.findAllByDisscusionId(id));
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveDiscussion(@RequestBody DiscussionReactionsCreateDTO discussionReactionsCreateDTO)
	{
		try {
			this.discussionReactionsService.save(discussionReactionsCreateDTO);
			messageResponse = String.format(this.messageSource.getMessage("createSuccessfully", null,null),"DiscussionReactions");
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDiscussion(@RequestBody DiscussionReactionsCreateDTO discussionReactionsCreateDTO,@PathVariable long id)
	{
		this.discussionReactionsService.update(discussionReactionsCreateDTO, id);
		messageResponse = String.format(this.messageSource.getMessage("updateSuccessfully", null,null),"DiscussionReactions");
		return ResponseEntity.ok(messageResponse);
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> deleteDiscussion(@RequestParam("discussion_id") Long discussionId,@RequestParam("userid") Long userId)
	{
		this.discussionReactionsService.delete(discussionId,userId);
		messageResponse = String.format(this.messageSource.getMessage("deleteSuccessfully", null,null),"DiscussionReactions");
		return ResponseEntity.ok(messageResponse);
	}
}
