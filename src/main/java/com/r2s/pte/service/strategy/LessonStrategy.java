package com.r2s.pte.service.strategy;

import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.entity.Category;

public interface LessonStrategy {

	void update(long id, LessonCreateDTO dto, Category category);

}
