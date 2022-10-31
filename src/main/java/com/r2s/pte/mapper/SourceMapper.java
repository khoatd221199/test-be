package com.r2s.pte.mapper;

import org.mapstruct.Mapper;

import com.r2s.pte.dto.SourceDTO;
import com.r2s.pte.entity.Source;

@Mapper(componentModel = "spring")
public interface SourceMapper {

	Source mapToEntity(SourceDTO sourceDTO);

}
