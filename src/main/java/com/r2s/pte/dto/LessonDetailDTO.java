package com.r2s.pte.dto;

import java.util.List;

import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.entity.QuestionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonDetailDTO {
	private LessonCreateDTO lesson;
	private QuestionGroup questionGroup;
	private  List<Question> question;
	private List<QuestionOption> questionOptions;
}
