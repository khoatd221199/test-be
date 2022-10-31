package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.r2s.pte.dto.VocabDictionaryDTO;
import com.r2s.pte.dto.VocabUserDTO;
import com.r2s.pte.dto.VocabUserViewDTO;
import com.r2s.pte.dto.VocabUserViewDetailDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Vocab;
import com.r2s.pte.entity.VocabUser;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.VocabDictionaryMapper;
import com.r2s.pte.mapper.VocabMapper;
import com.r2s.pte.mapper.VocabUserMapper;
import com.r2s.pte.repository.VocabRepository;
import com.r2s.pte.repository.VocabUserRepository;
import com.r2s.pte.service.CategoryService;
import com.r2s.pte.service.VocabService;
import com.r2s.pte.service.VocabUserService;

@Service
public class VocabUserServiceImpl implements VocabUserService {

	@Autowired
	private VocabUserRepository vocabUserRepository;
	@Autowired
	private VocabRepository vocabRepository;
	@Autowired
	private VocabUserMapper vocabUserMapper;
	@Autowired
	private VocabDictionaryMapper vocabDictionaryMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private VocabService vocabService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private VocabMapper vocabMapper;

	PaginationDTO paginationDTO;

	@Override
	public VocabUserDTO save(VocabUserDTO vocabUserDTO) {
		VocabDTO vocabDTO = vocabUserDTO.getVocab();
		Vocab vocab = vocabRepository.findByVocab(vocabDTO.getVocab());
		if(vocabUserRepository.existsByVocabId(vocab.getId())) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("AlreadyExists", null, null), String.valueOf(vocabDTO.getVocab())),TypeError.AlreadyExists);
		}
		String vocabStr = vocabDTO.getVocab();
		VocabUser vocabUserEntity = vocabUserMapper.map(vocabUserDTO);
		try {
			Optional<Vocab> vocabEntity = Optional.ofNullable(this.vocabMapper.map(vocabService.findByVocab(vocabStr)));
			vocabUserEntity.setVocab(vocabEntity.get());

		} catch (Exception e) {
			Vocab vocabSaved = vocabMapper.map(this.vocabService.save(vocabDTO));
			vocabUserEntity.setVocab(vocabSaved);
		}
		vocabUserEntity.setStatus(true);
		vocabUserEntity.setCreatedDate(LocalDateTime.now());
		vocabUserEntity.setCreatedBy(Long.valueOf(0));
		vocabUserEntity.setModifiedBy(Long.valueOf(0));
		vocabUserEntity.setModifiedDate(LocalDateTime.now());
		VocabUser vocabUserSaved = this.vocabUserRepository.save(vocabUserEntity);
		VocabUserDTO vocabUserDTOShow = this.vocabUserMapper.map(vocabUserSaved);
		Logger.logInfo.info("Add vocabuser by id ="+vocabUserSaved.getId()+"", "VocabUser");
		return vocabUserDTOShow;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public boolean existsById(Long id) {
		return false;
	}

	@Override
	public PaginationDTO findAll(RequestVocabUserDTO requestVocabDTO) {
		Pageable pageable = PageRequest.of(requestVocabDTO.getPageNumber(), requestVocabDTO.getLimit());// Page: 0 and
		List<VocabUserViewDTO> vocabUsers;
		Page<VocabUser> pageVocabUsers;

		if (requestVocabDTO.getCategory() != null) {
			Category category = this.categoryMapper.map(categoryService.findById(requestVocabDTO.getCategory()));
			vocabUsers = this.vocabUserRepository.findAllByUserIdAndCategoryAndStatus(0,category,true,pageable).stream()
					.map(item -> this.vocabUserMapper.mapToViewDTO(item)).collect(Collectors.toList());
			pageVocabUsers = this.vocabUserRepository.findAllByUserIdAndCategoryAndStatus(0,category,true,pageable); 
		} else {
			vocabUsers = this.vocabUserRepository.findAllByUserId(0,pageable).stream()
					.map(item -> this.vocabUserMapper.mapToViewDTO(item)).collect(Collectors.toList());
			pageVocabUsers = this.vocabUserRepository.findAllByUserId(0,pageable); 
		}
		PaginationDTO pageDTO = new PaginationDTO(vocabUsers, pageVocabUsers.isFirst(), pageVocabUsers.isLast(),
				pageVocabUsers.getTotalPages(), pageVocabUsers.getTotalElements(), pageVocabUsers.getSize(),
				pageVocabUsers.getNumber());

		return pageDTO;
	}

	@Override
	public boolean existsByVocab(String vocab) {
		return false;
	}

	@Override
	public VocabUserViewDetailDTO findByVocab(RequestVocabUserDTO requestVocabUserDTO) {
		VocabDTO vocabDTO = vocabService.findByVocab(requestVocabUserDTO.getVocab());
		VocabUserViewDetailDTO vocabUserViewDetailDTO;
		if (requestVocabUserDTO.getCategory() == null) {
			vocabUserViewDetailDTO = vocabUserMapper.mapToViewDetailDTO(
					this.vocabUserRepository.findByVocabId(vocabDTO.getId(), requestVocabUserDTO.getUserId()));
		} else {
			vocabUserViewDetailDTO = vocabUserMapper
					.mapToViewDetailDTO(this.vocabUserRepository.findByVocabIdAndCategoryId(vocabDTO.getId(),
							requestVocabUserDTO.getUserId(), requestVocabUserDTO.getCategory()));
		}
		if (vocabUserViewDetailDTO == null) {
			throw new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "VocabUser",
					"Vocab", String.valueOf(requestVocabUserDTO.getVocab())), TypeError.NotFound);
		}
		Logger.logInfo.info("Get vocabUserDetail by vocab {}", "VocabUser");

		return vocabUserViewDetailDTO;
	}

	
	@Override
	public VocabDictionaryDTO findDictionaryByVocab(RequestVocabUserDTO requestVocabUserDTO) {
		Vocab vocab = this.vocabRepository.findByVocab(requestVocabUserDTO.getVocab());
		VocabDictionaryDTO vocabDictionaryDTO;
		VocabUser vocabUser = this.vocabUserRepository.findByVocabIdAndCategoryId(vocab.getId(),requestVocabUserDTO.getUserId(), requestVocabUserDTO.getCategory());
		if (vocabUser == null) {
			vocabDictionaryDTO = vocabDictionaryMapper.map(vocab);
			vocabDictionaryDTO.setIsVocabUser(false);
			return vocabDictionaryDTO ;
		}
		Logger.logInfo.info("Get vocabUserDetail by vocab {}", "VocabUser");
		
		vocabDictionaryDTO = vocabDictionaryMapper.map(vocabUser);
		vocabDictionaryDTO.setIsVocabUser(true);
		return vocabDictionaryDTO;
	}
	
	@Override
	public VocabUserDTO findById(Long id) {
		Optional<VocabUser> entity = this.vocabUserRepository.findById(id);
		if (entity.isPresent())
			return vocabUserMapper.map(entity.get());
		else
			return null;
	}

	@Override
	public void update(VocabUserDTO vocabUserDTO, long id) {
		VocabUser vocabUser = vocabUserRepository.findById(id).get();
		if(vocabUserRepository.existsById(id)) {
			vocabUserDTO.setModifiedBy(Long.valueOf(0));
			vocabUserDTO.setModifiedDate(LocalDateTime.now());
			vocabUserDTO.setId(id);
			vocabUserDTO.setVocab(vocabMapper.map(vocabUser.getVocab()));
			if(vocabUserDTO.getCreatedDate()==null) {
				vocabUserDTO.setCreatedDate(vocabUser.getCreatedDate());
			}
			if(vocabUserDTO.getCreatedBy()==null) {
				vocabUserDTO.setCreatedBy(vocabUser.getCreatedBy());
			}
			if(vocabUserDTO.getCategory()==null) {
				vocabUserDTO.setCategory(categoryMapper.mapToDto(vocabUser.getCategory()));;
			}
			VocabUser vocabUpdate = vocabUserMapper.map(vocabUserDTO);
			vocabUpdate.setRevisedCount(vocabUser.getRevisedCount()+1);
			this.vocabUserRepository.save(vocabUpdate);
			Logger.logInfo.info("Update vocabuser by id ="+id+"", "VocabUser");

		}else {
			Logger.logWarn.info("Update vocabuser by id ="+id+" fail {}", "VocabUser");
			throw  new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "VocabUser", "vocabuser", String.valueOf(id)),
					TypeError.NotFound);
		}
		
	}

	@Override
	public void delete(long id) {
		VocabUser vocabUser = vocabUserRepository.findById(id).get();
		if(vocabUserRepository.existsById(id)) {
			vocabUser.setStatus(false);
			vocabUser.setRevisedCount(vocabUser.getRevisedCount()+1);
			this.vocabUserRepository.save(vocabUser);
			Logger.logInfo.info("Delete vocabuser by id ="+id+"", "VocabUser");
		}else {
			Logger.logWarn.info("Delete vocabuser by id ="+id+" fail {}", "VocabUser");
			throw  new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null), "VocabUser", "vocabuser", String.valueOf(id)),
					TypeError.NotFound);
		}
		
	}

}
