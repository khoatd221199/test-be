package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.VocabDictionaryDTO;
import com.r2s.pte.entity.Vocab;
import com.r2s.pte.entity.VocabUser;
import com.r2s.pte.mapper.VocabDictionaryMapper;

@Component
public class VocabDictionaryMapperImpl implements VocabDictionaryMapper {

	@Override
	public VocabDictionaryDTO map(Vocab vocab) {
		if (vocab == null)
			return null;
		VocabDictionaryDTO vocabDictionaryDTO = new VocabDictionaryDTO();
		vocabDictionaryDTO.setVocab(vocab.getVocab());
		vocabDictionaryDTO.setCountryId(vocab.getCountryId());
		vocabDictionaryDTO.setIpa(vocab.getIpa());
		vocabDictionaryDTO.setDefinition(vocab.getDefinition());
		vocabDictionaryDTO.setExample(vocab.getExample());
		vocabDictionaryDTO.setIsPhrasalWord(vocab.getIsPhrasalWord());;
		return vocabDictionaryDTO;
	}

	@Override
	public VocabDictionaryDTO map(VocabUser vocabUser) {
		if (vocabUser == null)
			return null;
		VocabDictionaryDTO vocabDictionaryDTO = new VocabDictionaryDTO();
		vocabDictionaryDTO.setVocab(vocabUser.getVocab().getVocab());
		vocabDictionaryDTO.setCountryId(vocabUser.getVocab().getCountryId());
		vocabDictionaryDTO.setIpa(vocabUser.getVocab().getIpa());
		if (vocabUser.getDefinitionUser() == null) {
			vocabDictionaryDTO.setDefinition(vocabUser.getVocab().getDefinition());
		} else {
			vocabDictionaryDTO.setDefinition(vocabUser.getDefinitionUser());
		}
		// check example of user exist
		if (vocabUser.getExampleUser() == null) {
			vocabDictionaryDTO.setExample(vocabUser.getVocab().getExample());
		} else {
			vocabDictionaryDTO.setExample(vocabUser.getExampleUser());
		}
		vocabDictionaryDTO.setIsPhrasalWord(vocabUser.getVocab().getIsPhrasalWord());;
		return vocabDictionaryDTO;
	}

}
