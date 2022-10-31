package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.VocabDictionaryDTO;
import com.r2s.pte.entity.Vocab;
import com.r2s.pte.entity.VocabUser;

@Mapper(componentModel = "spring")
public interface VocabDictionaryMapper {
	VocabDictionaryDTO map(Vocab vocab);

	VocabDictionaryDTO map(VocabUser vocabUser);
}
