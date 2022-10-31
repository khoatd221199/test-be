package com.r2s.pte.mapper.impl;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.SourceDTO;
import com.r2s.pte.entity.Source;
import com.r2s.pte.mapper.SourceMapper;

@Component
public class SourceMapperImpl implements SourceMapper {
	
	@Override
	public Source mapToEntity(SourceDTO dto) {
		if (dto == null) {
			return null;
		}
		Source source = new Source();
		source.setSourceName(dto.getName());
		source.setOriginalSourceMediaLink(dto.getOriginalSourceMediaLink());
		source.setSourceWebsite(dto.getWebsite());
		source.setLessonOriginalSourceLink(dto.getLessonOriginalSourceLink());
		source.setDescription(dto.getDescription());
		return source;
	}

}
