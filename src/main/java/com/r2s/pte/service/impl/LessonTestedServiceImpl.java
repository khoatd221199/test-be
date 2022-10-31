package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.LessonTestedDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.LessonTested;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.LessonTestedMapper;
import com.r2s.pte.repository.LessonTestedRepository;
import com.r2s.pte.service.LessonService;
import com.r2s.pte.service.LessonTestedService;

@Service
public class LessonTestedServiceImpl implements LessonTestedService {
	@Autowired
	private LessonTestedRepository lessonTestedRepository;
	@Autowired
	private LessonTestedMapper lessonTestedMapper;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LessonService lessonService;

	@Override
	public LessonTestedDTO save(LessonTestedDTO dto) {
		LessonTested entity = lessonTestedMapper.mapToEntity(dto);
		entity.setModifiedBy(UserContext.getId());
		entity.setModifiedDate(LocalDateTime.now());
		entity.setCreatedBy(UserContext.getId());
		entity.setCreatedDate(LocalDateTime.now());
		LessonTested lessonTestedSaved = this.lessonTestedRepository.save(entity);
		LessonTestedDTO lessonTestedDTO = this.lessonTestedMapper.mapToDTO(lessonTestedSaved);
		return lessonTestedDTO;
	}

	@Override
	public List<LessonTestedDTO> getTestedByLessonId(Long id) {
		List<LessonTested> lessonTesteds = this.lessonTestedRepository.findByLessonId(id);
		// switch to List Lesson Tested DTO
		List<LessonTestedDTO> lessonTestedDTOs = lessonTesteds.stream()
				.map(item -> this.lessonTestedMapper.mapToDTO(item)).collect(Collectors.toList());
		return lessonTestedDTOs;
	}

	@Override
	public Long countByLesson(Long id) {
		return this.lessonTestedRepository.coutByLessonId(id);
	}

	@Override
	public List<LessonTestedDTO> getLessonAndUser(Long idLesson, Long idUser) {
		boolean lessonExists = this.lessonService.isExists(idLesson);
		if (!lessonExists)
			throw new ErrorMessageException(String.format(this.messageSource.getMessage("NotFound", null, null),
					"Lesson", "Id", String.valueOf(idLesson)), TypeError.NotFound);
		List<LessonTested> lessonTesteds = this.lessonTestedRepository.getByLessonAndUser(idLesson, idUser);
		List<LessonTestedDTO> lessonTestedDTOs = lessonTesteds.stream()
				.map(lessonTested -> this.lessonTestedMapper.mapToDTO(lessonTested)).collect(Collectors.toList());
		return lessonTestedDTOs;
	}

	@Override
	public LessonTestedDTO findById(Long id) {

		LessonTested lessonTested = this.lessonTestedRepository.findById(id).orElseThrow(
				() -> new ErrorMessageException(String.format(messageSource.getMessage("NotFound", null, null),
						"Lesson Tested", "id", String.valueOf(id)), TypeError.NotFound));
		LessonTestedDTO dto = this.lessonTestedMapper.mapToDTO(lessonTested);
		return dto;
	}

	@Override
	public void deleteById(Long id) {
		this.lessonTestedRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return this.lessonTestedRepository.existsById(id);
	}

	@Override
	public void deleteByLessonId(Long lessonId) {
		List<LessonTested> lessonTesteds = this.lessonTestedRepository.findByLessonId(lessonId);
		if (lessonTesteds != null && lessonTesteds.size() > 0) {
			lessonTesteds.forEach(lessonTested -> {
				lessonTested.setLesson(null);
				this.lessonTestedRepository.save(lessonTested);
				this.lessonTestedRepository.deleteById(lessonTested.getId());
			});
		}
	}

}
