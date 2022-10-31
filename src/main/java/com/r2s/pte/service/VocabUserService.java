package com.r2s.pte.service;

import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.RequestVocabUserDTO;
import com.r2s.pte.dto.VocabDictionaryDTO;
import com.r2s.pte.dto.VocabUserDTO;
import com.r2s.pte.dto.VocabUserViewDetailDTO;

public interface VocabUserService {
	
	long count();

	boolean existsById(Long id);

	PaginationDTO findAll(RequestVocabUserDTO requestVocabDTO);

	boolean existsByVocab(String vocab);

	VocabUserViewDetailDTO findByVocab(RequestVocabUserDTO requestVocabDTO);
	
	VocabDictionaryDTO findDictionaryByVocab(RequestVocabUserDTO requestVocabDTO);

	VocabUserDTO save(VocabUserDTO vocabUserDTO);
	
	VocabUserDTO findById(Long id);
	
	void update(VocabUserDTO vocabUserDTO, long id);
		
	void delete(long id);

}
