package com.r2s.pte.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.LessonCreationDTO;
import com.r2s.pte.dto.RequestLessonDTO;
import com.r2s.pte.service.LessonCategoryService;
import com.r2s.pte.service.LessonService;

@RestController
@RequestMapping(value = { "api-dev/lesson", "api-pro/lesson", "api/lesson" })
public class LessonController {
	@Autowired
	private LessonService lessonService;
	@Autowired
	private LessonCategoryService lessonCategoryService;
	@Autowired
	private MessageSource messageSource;
	private final String entity = "Lesson";
	private String messageResponse = "";

	@GetMapping(value = "/lessoncategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(this.lessonCategoryService.findAll());
	}

	@GetMapping(value = "/lessondetail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> find(@PathVariable Long id) {
		return ResponseEntity.ok(this.lessonService.getDetailById(id));
	}

	@PostMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll(@RequestBody RequestLessonDTO requestDTO) {
		return ResponseEntity.ok(this.lessonService.findByRequestLessonDTO(requestDTO));
	}

	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@RequestPart(name = "lesson", required = true) String lesson,
			@RequestPart(name = "image", required = false) MultipartFile image,
			@RequestPart(name = "shadowing", required = false) MultipartFile shadowing) {
		LessonCreationDTO lessonCreate = this.lessonService.readJson(lesson, image, shadowing);
		this.lessonService.save(lessonCreate);
		messageResponse = String.format(this.messageSource.getMessage("createSuccessfully", null, null), entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
	}
	@PutMapping(value = "{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> update(@RequestPart(name = "lesson", required = true) String lesson,
			@RequestPart(name = "image", required = false) MultipartFile image,
			@RequestPart(name = "shadowing", required = false) MultipartFile shadowing) {
		LessonCreationDTO lessonCreate = this.lessonService.readJson(lesson, image, shadowing);
		this.lessonService.save(lessonCreate);
		messageResponse = String.format(this.messageSource.getMessage("createSuccessfully", null, null), entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody LessonCreateDTO lesson, @PathVariable long id) {
		this.lessonService.update(id, lesson);
		messageResponse = String.format(this.messageSource.getMessage("updateSuccessfully", null, null), entity);
		return ResponseEntity.ok(messageResponse);
	}

//	@PutMapping(value = "di/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
//	public ResponseEntity<?> updateDI(@RequestPart(name = "lesson", required = true) String lessonCreateDTO,
//			@RequestPart(name = "image", required = false) MultipartFile image, @PathVariable long id) {
//		LessonCreateDIDTO lesson = this.lessonService.readJson(lessonCreateDTO, image);
//		this.lessonService.update(lesson, id);
//		messageResponse = String.format(this.messageSource.getMessage("updateSuccessfully", null, null), entity);
//		return ResponseEntity.ok(messageResponse);
//	}

	@PutMapping(value = "{id}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> active(@PathVariable Long id) {
		this.lessonService.active(id);
		messageResponse = String.format(this.messageSource.getMessage("activeSuccessfully", null, null), entity);
		return ResponseEntity.ok(messageResponse);
	}

	@PutMapping(value = "{id}/disable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> disable(@PathVariable Long id) {
		this.lessonService.disableById(id);
		messageResponse = String.format(this.messageSource.getMessage("disableSuccessfully", null, null), entity);
		return ResponseEntity.ok(messageResponse);
	}

	@GetMapping("/default")
	public ResponseEntity<?> getLessonDetailDefault() {
		return ResponseEntity.ok(this.lessonService.getLessonDetailDefault());
	}

	@DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		this.lessonService.deleteById(id);
		messageResponse = String.format(this.messageSource.getMessage("deleteSuccessfully", null, null), entity);
		return ResponseEntity.ok(messageResponse);
	}

	@DeleteMapping(value = "v2", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteByListId(@RequestParam(value = "ids", required = true) List<Long> ids) {
		this.lessonService.deleteByListId(ids);
		messageResponse = String.format(this.messageSource.getMessage("deleteSuccessfully", null, null), entity);
		return ResponseEntity.ok(messageResponse);
	}
}
