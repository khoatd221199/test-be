package com.r2s.pte.dto;

import java.util.ArrayList;
import java.util.List;

import com.r2s.pte.entity.Lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultLessonQueryDTO {
	private List<Lesson>  lessons = new ArrayList<>();
	private Integer totalItems;

}
