package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.service.CategoryService;

@RestController
@RequestMapping(value = { "api-dev/category", "api-pro/category", "api/category" })
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(this.categoryService.getAll());
	}

	@GetMapping("/parent/{id}")
	public ResponseEntity<?> findId(@PathVariable Long id) {

		return ResponseEntity.ok(this.categoryService.getByParentId(id));
	}

}
