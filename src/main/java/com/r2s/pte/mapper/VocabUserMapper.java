package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.VocabUserDTO;
import com.r2s.pte.dto.VocabUserViewDTO;
import com.r2s.pte.dto.VocabUserViewDetailDTO;
import com.r2s.pte.entity.VocabUser;

@Mapper(componentModel = "spring")
public interface VocabUserMapper {
	VocabUser map(VocabUserDTO vocabUserDTO);

	VocabUserDTO map(VocabUser vocabUser);
	
	VocabUserViewDTO mapToViewDTO(VocabUser vocabUser);
	
	VocabUserViewDetailDTO mapToViewDetailDTO(VocabUser vocabUser);
	
}
