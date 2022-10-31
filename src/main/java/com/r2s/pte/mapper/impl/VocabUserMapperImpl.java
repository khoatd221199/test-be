package com.r2s.pte.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.VocabDTO;
import com.r2s.pte.dto.VocabUserDTO;
import com.r2s.pte.dto.VocabUserViewDTO;
import com.r2s.pte.dto.VocabUserViewDetailDTO;
import com.r2s.pte.dto.VocabViewDTO;
import com.r2s.pte.entity.VocabUser;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.VocabMapper;
import com.r2s.pte.mapper.VocabUserMapper;
import com.r2s.pte.repository.CategoryRepository;

@Component
public class VocabUserMapperImpl implements VocabUserMapper {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private VocabMapper vocabMapper;

	@Override
	public VocabUser map(VocabUserDTO dto) {
		if (dto == null)
			return null;
		VocabUser vocabUser = new VocabUser();
		vocabUser.setId(dto.getId());
		vocabUser.setDefinitionUser(dto.getDefinitionUser());
		vocabUser.setExampleUser(dto.getExampleUser());
		vocabUser.setStatus(dto.getStatus());
		vocabUser.setUserId(dto.getUserId());
		vocabUser.setVocab(vocabMapper.map(dto.getVocab()));
		vocabUser.setRevisedCount(dto.getRevisedCount());
		vocabUser.setPriority(dto.getPriority());
		CategoryDTO categoryDTO = dto.getCategory();
		vocabUser.setCategory(categoryRepository.findById(categoryDTO.getId()).get());
		vocabUser.setCreatedBy(dto.getCreatedBy());
		vocabUser.setCreatedDate(dto.getCreatedDate());
		vocabUser.setModifiedBy(dto.getModifiedBy());
		vocabUser.setModifiedDate(dto.getModifiedDate());
		return vocabUser;
	}

	@Override
	public VocabUserDTO map(VocabUser dto) {
		if (dto == null)
			return null;
		VocabUserDTO vocabUser = new VocabUserDTO();
		VocabDTO vocabDTO = this.vocabMapper.map(dto.getVocab());
		vocabUser.setVocab(vocabDTO);
		vocabUser.setId(dto.getId());
		vocabUser.setDefinitionUser(dto.getDefinitionUser());
		vocabUser.setExampleUser(dto.getExampleUser());
		vocabUser.setStatus(dto.getStatus());
		vocabUser.setUserId(dto.getUserId());
		vocabUser.setVocab(vocabMapper.map(dto.getVocab()));
		vocabUser.setRevisedCount(dto.getRevisedCount());
		vocabUser.setPriority(dto.getPriority());
		CategoryDTO categoryDTO = categoryMapper.mapToDto(dto.getCategory());
		vocabUser.setCategory(categoryDTO);
		vocabUser.setCreatedBy(dto.getCreatedBy());
		vocabUser.setCreatedDate(dto.getCreatedDate());
		vocabUser.setModifiedBy(dto.getModifiedBy());
		vocabUser.setModifiedDate(dto.getModifiedDate());
		return vocabUser;
	}

	@Override
	public VocabUserViewDTO mapToViewDTO(VocabUser vocabUser) {
		if (vocabUser == null)
			return null;
		VocabUserViewDTO vocabUserViewDTO = new VocabUserViewDTO();
		VocabViewDTO vocabViewDTO = this.vocabMapper.mapToViewDTO(vocabUser.getVocab());
		vocabUserViewDTO.setVocab(vocabViewDTO);
		vocabUserViewDTO.setId(vocabUser.getId());
		return vocabUserViewDTO;
	}

	@Override
	public VocabUserViewDetailDTO mapToViewDetailDTO(VocabUser vocabUser) {
		if (vocabUser == null)
			return null;
		VocabUserViewDetailDTO vocabUserViewDetailDTO = new VocabUserViewDetailDTO();
		VocabViewDTO vocabViewDTO = this.vocabMapper.mapToViewDTO(vocabUser.getVocab());
		vocabUserViewDetailDTO.setVocab(vocabViewDTO);
		vocabUserViewDetailDTO.setId(vocabUser.getId());
		// check definition of user exist
		if (vocabUser.getDefinitionUser() == null) {
			vocabUserViewDetailDTO.setDefinition(vocabUser.getVocab().getDefinition());
		} else {
			vocabUserViewDetailDTO.setDefinition(vocabUser.getDefinitionUser());
		}
		// check example of user exist
		if (vocabUser.getExampleUser() == null) {
			vocabUserViewDetailDTO.setExample(vocabUser.getVocab().getExample());
		} else {
			vocabUserViewDetailDTO.setExample(vocabUser.getExampleUser());
		}
		vocabUserViewDetailDTO.setStatus(vocabUser.getStatus());
		vocabUserViewDetailDTO.setUserId(vocabUser.getUserId());
		vocabUserViewDetailDTO.setRevisedCount(vocabUser.getRevisedCount());
		vocabUserViewDetailDTO.setPriority(vocabUser.getPriority());
		CategoryDTO categoryDTO = categoryMapper.mapToDto(vocabUser.getCategory());
		vocabUserViewDetailDTO.setCategory(categoryDTO);
		vocabUserViewDetailDTO.setCreatedBy(vocabUser.getCreatedBy());
		vocabUserViewDetailDTO.setCreatedDate(vocabUser.getCreatedDate());
		vocabUserViewDetailDTO.setModifiedBy(vocabUser.getModifiedBy());
		vocabUserViewDetailDTO.setModifiedDate(vocabUser.getModifiedDate());
		return vocabUserViewDetailDTO;
	}
	
}
