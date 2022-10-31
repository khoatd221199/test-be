package com.r2s.pte.service;

import com.r2s.pte.dto.SourceDTO;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.Source;

public interface SourceService {

	Source save(SourceDTO dto, Lesson lesson);

}
