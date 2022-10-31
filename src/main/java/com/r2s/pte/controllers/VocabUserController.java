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
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.dto.RequestVocabUserDTO;
import com.r2s.pte.dto.VocabDictionaryDTO;
import com.r2s.pte.dto.VocabUserDTO;
import com.r2s.pte.service.VocabUserService;


@RestController
@RequestMapping(value = {"api-dev/vocabuser","api-pro/vocabuser","api/vocabuser"})
public class VocabUserController {
	@Autowired
	private VocabUserService vocabUserService;
	@Autowired
	private MessageSource messageSource;
	private String messageResponse = "";

	@PostMapping("vocabusers")
	public ResponseEntity<?> findAll(@RequestBody RequestVocabUserDTO requestVocabDTO)
	{
		
		return ResponseEntity.ok(this.vocabUserService.findAll(requestVocabDTO));
	}
	
	@PostMapping("vocabuserdetail")
	public ResponseEntity<?> findByVocab(@RequestBody RequestVocabUserDTO requestVocabDTO)
	{

		return ResponseEntity.ok(this.vocabUserService.findByVocab(requestVocabDTO));
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveVocabUser(@RequestBody VocabUserDTO vocabUserDTO)
	{

		this.vocabUserService.save(vocabUserDTO);
		messageResponse = String.format(this.messageSource.getMessage("createSuccessfully", null,null),"VocabUser");
		return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
	}
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> findByUserId(@PathVariable Long id)
	{

		return ResponseEntity.ok(this.vocabUserService.findById(id));
	}
	
	@PostMapping("/dictionary")
	public ResponseEntity<?> findDictionryByVocab(@RequestBody RequestVocabUserDTO requestVocabUserDTO)
	{
		VocabDictionaryDTO vocabDictionaryDTO = this.vocabUserService.findDictionaryByVocab(requestVocabUserDTO);
		return ResponseEntity.ok(vocabDictionaryDTO);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVocabUser(@PathVariable long id)
	{
		this.vocabUserService.delete(id);
		messageResponse = String.format(this.messageSource.getMessage("deleteSuccessfully", null,null),"VocabUser");
		return ResponseEntity.ok(messageResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVocabUser(@RequestBody VocabUserDTO vocabUserDTO, @PathVariable long id)
	{
		this.vocabUserService.update(vocabUserDTO, id);
		messageResponse = String.format(this.messageSource.getMessage("updateSuccessfully", null,null),"VocabUser");
		return ResponseEntity.ok(messageResponse);
	}
	


}
