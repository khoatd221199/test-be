package com.r2s.pte.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.pte.dto.SourceDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.Source;
import com.r2s.pte.mapper.SourceMapper;
import com.r2s.pte.repository.SourceRepository;
import com.r2s.pte.service.SourceService;
@Service
public class SourceServiceImpl implements SourceService{
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private SourceMapper sourceMapper;
	
	@Override
	public Source save(SourceDTO dto,Lesson lesson)
	{
		Source source = sourceMapper.mapToEntity(dto);
		source.setModifiedDate(LocalDateTime.now());
		source.setModifiedBy(UserContext.getId());
		source.setCreatedBy(UserContext.getId());
		source.setCreatedDate(LocalDateTime.now());
		source.setLesson(lesson);
		return this.sourceRepository.save(source);
	}
}
