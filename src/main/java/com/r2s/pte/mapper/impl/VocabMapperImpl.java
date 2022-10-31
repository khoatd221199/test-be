package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.VocabDTO;
import com.r2s.pte.dto.VocabViewDTO;
import com.r2s.pte.dto.VocabViewDetailDTO;
import com.r2s.pte.entity.Vocab;
import com.r2s.pte.mapper.VocabMapper;
@Component
public class VocabMapperImpl implements VocabMapper {
	
	@Override
	public Vocab map(VocabDTO dto)
	{
		if(dto == null)
			return null;
		Vocab vocab = new Vocab();
		vocab.setId(dto.getId());
		vocab.setVocab(dto.getVocab());
		vocab.setCountryId(dto.getCountryId());
		vocab.setIpa(dto.getIpa());
		vocab.setDefinition(dto.getDefinition());
		vocab.setPriority(dto.getPriority());
		vocab.setIsPhrasalWord(dto.getIsPhrasalWord());
		vocab.setExample(dto.getExample());
		vocab.setCreatedBy(dto.getCreatedBy());
		vocab.setCreatedDate(dto.getCreatedDate());
		vocab.setModifiedBy(dto.getModifiedBy());
		vocab.setModifiedDate(dto.getModifiedDate());
		return vocab;
	}
	@Override
	public VocabDTO map(Vocab entity)
	{
		if(entity == null)
			return null;
		VocabDTO dto = new VocabDTO();
		dto.setId(entity.getId());
		dto.setVocab(entity.getVocab());
		dto.setCountryId(entity.getCountryId());
		dto.setIpa(entity.getIpa());
		dto.setDefinition(entity.getDefinition());
		dto.setPriority(entity.getPriority());
		dto.setIsPhrasalWord(entity.getIsPhrasalWord());
		dto.setExample(entity.getExample());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		return dto;
	}
	@Override
	public VocabViewDTO mapToViewDTO(Vocab vocab) {
		if(vocab == null)
			return null;
		VocabViewDTO dto = new VocabViewDTO();
		dto.setId(vocab.getId());
		dto.setVocab(vocab.getVocab());
		dto.setCountryId(vocab.getCountryId());
		dto.setIpa(vocab.getIpa());
		dto.setIsPhrasalWord(vocab.getIsPhrasalWord());

		return dto;
	}
	@Override
	public VocabViewDetailDTO mapToViewDetailDTO(Vocab vocab) {
		if(vocab == null)
			return null;
		VocabViewDetailDTO dto = new VocabViewDetailDTO();
		dto.setId(vocab.getId());
		dto.setVocab(vocab.getVocab());
		dto.setCountryId(vocab.getCountryId());
		dto.setIpa(vocab.getIpa());
		dto.setDefinition(vocab.getDefinition());
		dto.setPriority(vocab.getPriority());
		dto.setIsPhrasalWord(vocab.getIsPhrasalWord());
		dto.setExample(vocab.getExample());
		dto.setCreatedBy(vocab.getCreatedBy());
		dto.setCreatedDate(vocab.getCreatedDate());
		dto.setModifiedBy(vocab.getModifiedBy());
		dto.setModifiedDate(vocab.getModifiedDate());
		return dto;
	}
}
