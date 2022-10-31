package com.r2s.pte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.dto.RequestVocabUserDTO;
import com.r2s.pte.dto.VocabDTO;
import com.r2s.pte.service.VocabService;


@RestController
@RequestMapping(value = {"api-dev/vocab","api-pro/vocab","api/vocab"})
public class VocabController {	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private VocabService vocabService;
	private String messageResponse = "";
	@PostMapping("vocabs")
	public ResponseEntity<?> findAll(@RequestBody RequestVocabUserDTO requestVocabDTO)
	{
		
		return ResponseEntity.ok(this.vocabService.findAll(requestVocabDTO));
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveVocabUser(@RequestBody VocabDTO vocabDTO)
	{
		this.vocabService.save(vocabDTO);
		messageResponse = String.format(this.messageSource.getMessage("createSuccessfully", null,null),"Vocab");
		return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVocab(@RequestBody VocabDTO vocabDTO, @PathVariable long id)
	{
		this.vocabService.update(vocabDTO, id);
		messageResponse = String.format(this.messageSource.getMessage("updateSuccessfully", null,null),"Vocab");
		return ResponseEntity.ok(messageResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVocab(@PathVariable long id)
	{
		this.vocabService.delete(id);
		messageResponse = String.format(this.messageSource.getMessage("deleteSuccessfully", null,null),"Vocab");
		return ResponseEntity.ok(messageResponse);
	}
	

}
