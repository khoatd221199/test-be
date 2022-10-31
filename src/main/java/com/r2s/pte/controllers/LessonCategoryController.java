package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.service.LessonCategoryService;
import com.r2s.pte.service.LessonService;

@RestController
@RequestMapping(value = {"api-dev/lesson-category","api-pro/lesson-category","api/lesson-category"})
public class LessonCategoryController {
	@Autowired
	private LessonCategoryService lessonCategoryService;
	@Autowired
	private LessonService lessonService;
	@GetMapping("lesson/{id}")
	public ResponseEntity<?> getId(@PathVariable long id)
	{
		return ResponseEntity.ok(this.lessonService.findById(id));
	}
	@GetMapping("api/lesson-category")
	public ResponseEntity<?> index()
	{
		return ResponseEntity.ok(this.lessonCategoryService.findAll());
	}

}
