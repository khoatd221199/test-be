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

import com.r2s.pte.dto.DiscussionCreateDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.service.DiscussionService;

@RestController
@RequestMapping(value = {"api-dev/discussion","api-pro/discussion","api/discussion"})
public class DicussionController {
	@Autowired
	private DiscussionService discussionService;

	@Autowired
	private MessageSource messageSource;
	private String messageResponse = "";
	@GetMapping("lesson/{lessonId}")
	public ResponseEntity<?> findAllDiscussionByLesson(@PathVariable long lessonId,@RequestParam("page") int page,
			@RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.discussionService.findAllDiscussionByLessonId(lessonId,page,limit));
	}
	
	@GetMapping("board/lesson/{lessonId}")
	public ResponseEntity<?> findAllBoardByLesson(@PathVariable long lessonId,@RequestParam("page") int page,
			@RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.discussionService.findAllDiscussionIncludeAnswerByLessonId(lessonId,null,page,limit));
	}
	@GetMapping("me/lesson/{lessonId}")
	public ResponseEntity<?> findAllMedByLesson(@PathVariable long lessonId,@RequestParam("page") int page,
			@RequestParam("limit") int limit)
	{
		return ResponseEntity.ok(this.discussionService.findAllDiscussionIncludeAnswerByLessonId(lessonId,UserContext.getId(),page,limit));
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveDiscussion(@RequestBody DiscussionCreateDTO discussionDTO)
	{
		this.discussionService.save(discussionDTO);
		messageResponse = String.format(this.messageSource.getMessage("createSuccessfully", null,null),"Discussion");
		return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDiscussion(@RequestBody DiscussionCreateDTO discussionDTO,@PathVariable long id)
	{
		this.discussionService.update(discussionDTO, id);
		messageResponse = String.format(this.messageSource.getMessage("updateSuccessfully", null,null),"Discussion");
		return ResponseEntity.ok(messageResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDiscussion(@PathVariable long id)
	{
		this.discussionService.delete(id);
		messageResponse = String.format(this.messageSource.getMessage("deleteSuccessfully", null,null),"Discussion");
		return ResponseEntity.ok(messageResponse);
	}
}
