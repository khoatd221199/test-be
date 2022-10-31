package com.r2s.pte.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.Logger;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.RequestVocabUserDTO;
import com.r2s.pte.dto.VocabDTO;
import com.r2s.pte.entity.Vocab;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.VocabMapper;
import com.r2s.pte.repository.VocabRepository;
import com.r2s.pte.service.VocabService;

@Service
public class VocabServiceImpl implements VocabService {
	@Autowired
	private VocabRepository vocabRepository;
	@Autowired
	private VocabMapper vocabMapper;
	@Autowired
	private MessageSource messageSource;

	@Override
	public long count() {
		Long count = vocabRepository.count();
		return count;
	}

	@Override
	public boolean existsById(Long id) {
		return this.vocabRepository.existsById(id);
	}

	@Override
	public VocabDTO findById(Long id) {
		Vocab vocab = vocabRepository.findById(id).orElseThrow(() -> new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null, null), "Lesson", "Id", String.valueOf(id)),
				TypeError.NotFound));
		Logger.logInfo.info("Get vocab by id {}", "Vocab");
		return vocabMapper.map(vocab);
	}
	
	@Override
	public VocabDTO findByVocab(String vocabStr)
	{
		Vocab vocab = this.vocabRepository.findByVocab(vocabStr);
		if(vocab == null) {
			Logger.logWarn.info("Get vocab by vocab ="+vocabStr+" fail {}", "Vocab");
			throw  new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "Vocab", "vocab", String.valueOf(vocabStr)),
					TypeError.NotFound);
		}
		
		Logger.logInfo.info("Get vocab by vocab: "+vocabStr,"Vocab");
		return vocabMapper.map(vocab);
	}


	@Override
	public VocabDTO  save(VocabDTO vocabDTO) {
		
		if(vocabRepository.existsByVocab(vocabDTO.getVocab())) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("AlreadyExists", null, null), String.valueOf(vocabDTO.getVocab())),TypeError.AlreadyExists);
		}
		vocabDTO.setModifiedBy(Long.valueOf(0));
		vocabDTO.setModifiedDate(LocalDateTime.now());
		vocabDTO.setCreatedBy(Long.valueOf(0));
		vocabDTO.setCreatedDate(LocalDateTime.now());
		if(vocabDTO.getCountryId()==null) {
			vocabDTO.setCountryId((long) 1);
		}
		Vocab vocab= vocabMapper.map(vocabDTO);
		vocabDTO = vocabMapper.map(this.vocabRepository.save(vocab));
		Logger.logInfo.info("Add vocab with id ="+vocab.getVocab()+"", "Vocab");
		return vocabDTO;
	}

	@Override
	public boolean existsByVocab(String vocab) {
		return vocabRepository.existsByVocab(vocab);
	}

	@Override
	public PaginationDTO findAll(RequestVocabUserDTO requestVocabDTO) {
		Pageable pageable = PageRequest.of(requestVocabDTO.getPageNumber(), requestVocabDTO.getLimit());// Page: 0 and
		Page<Vocab> pageVocab = this.vocabRepository.findAll(pageable);
		PaginationDTO pageDTO = new PaginationDTO(pageVocab.toList(), pageVocab.isFirst(), pageVocab.isLast(),
				pageVocab.getTotalPages(), pageVocab.getTotalElements(), pageVocab.getSize(),
				pageVocab.getNumber());

		return pageDTO;
	}

	@Override
	public void update(VocabDTO vocabDTO, long id) {
		Vocab vocab = vocabRepository.findById(id).get();
		if(vocabRepository.existsById(id)) {
			vocabDTO.setModifiedBy(Long.valueOf(0));
			vocabDTO.setModifiedDate(LocalDateTime.now());
			vocabDTO.setId(id);
			vocabDTO.setVocab(vocab.getVocab());
			if(vocabDTO.getCountryId()==null) {
				vocabDTO.setCountryId((long) 1);
			}
			if(vocabDTO.getCreatedDate()==null) {
				vocabDTO.setCreatedDate(vocab.getCreatedDate());
			}
			if(vocabDTO.getCreatedBy()==null) {
				vocabDTO.setCreatedBy(vocab.getCreatedBy());
			}
			Vocab vocabUpdate = vocabMapper.map(vocabDTO);
			this.vocabRepository.save(vocabUpdate);
			Logger.logInfo.info("Update vocab with id ="+id+"", "Vocab");
		}else {
			throw  new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "Vocab", "vocab", String.valueOf(id)),
					TypeError.NotFound);
		}
	
		
	}

	@Override
	public void delete(long id) {
		if(vocabRepository.existsById(id)) {
			vocabRepository.deleteById(id);
			Logger.logInfo.info("Delete vocab with id ="+id+"", "Vocab");
		}else {
			throw  new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "VocabUser", "vocabuser", String.valueOf(id)),
					TypeError.NotFound);
		}
		
		
	}
	
	

}
