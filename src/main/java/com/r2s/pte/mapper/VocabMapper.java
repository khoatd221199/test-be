package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.VocabDTO;
import com.r2s.pte.dto.VocabViewDTO;
import com.r2s.pte.dto.VocabViewDetailDTO;
import com.r2s.pte.entity.Vocab;

@Mapper(componentModel = "spring")
public interface VocabMapper {
	Vocab map(VocabDTO vocabDTO);

	VocabDTO map(Vocab vocab);
	
	VocabViewDTO mapToViewDTO(Vocab vocab);
	
	VocabViewDetailDTO mapToViewDetailDTO(Vocab vocab);

}
