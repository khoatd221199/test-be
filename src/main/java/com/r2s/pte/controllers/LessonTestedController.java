package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.dto.LessonTestedDTO;
import com.r2s.pte.service.LessonTestedService;


@RestController
@RequestMapping(value = {"api-dev/leaner/tested","api-pro/leaner/tested","api/leaner/tested"})
public class LessonTestedController {
	@Autowired
	private LessonTestedService lessonTestedService;
	@Autowired
	private MessageSource messageSource;
	private String entity = "Lesson Tested";
	String messageResponse = "";

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody LessonTestedDTO dto) {
		this.lessonTestedService.save(dto);
		messageResponse = String.format(messageSource.getMessage("createSuccessfully", null, null), entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
	}

	@GetMapping("/view-all/{idLesson}")
	public ResponseEntity<?> getByLessonId(@PathVariable long idLesson) {
		return ResponseEntity.ok(this.lessonTestedService.getTestedByLessonId(idLesson));
	}

	@GetMapping("/lesson/{idLesson}")
	public ResponseEntity<?> countByLesson(@PathVariable Long idLesson) {
		return ResponseEntity.ok(this.lessonTestedService.countByLesson(idLesson));
	}

	@GetMapping("{idLesson}/{idUser}")
	public ResponseEntity<?> getLessonTestedById(@PathVariable Long idLesson, @PathVariable Long idUser) {
		return ResponseEntity.ok(this.lessonTestedService.getLessonAndUser(idLesson, idUser));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		this.lessonTestedService.deleteById(id);
		messageResponse = String.format(messageSource.getMessage("deleteSuccessfully", null, null), entity);
		return ResponseEntity.ok(messageResponse);
	}

}
