package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.service.QuestionTypeService;

@RestController
@RequestMapping(value = { "api-dev/question-type", "api/question-type", "api-pro/question-type" })
public class QuestionTypeController {
	@Autowired
	private QuestionTypeService questionTypeService;

	@GetMapping("")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(this.questionTypeService.findAll());
	}
}
