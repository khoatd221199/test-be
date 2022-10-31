package com.r2s.pte.service;

import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.RequestVocabUserDTO;
import com.r2s.pte.dto.VocabDTO;

public interface VocabService {
	long count();

	boolean existsById(Long id);

	VocabDTO findById(Long id);

	boolean existsByVocab(String vocab);

	VocabDTO findByVocab(String vocab);

	VocabDTO save(VocabDTO dto);
	
	PaginationDTO findAll(RequestVocabUserDTO requestVocabDTO);
	
	void update(VocabDTO vocabDTO, long id);
	
	void delete(long id);

}
