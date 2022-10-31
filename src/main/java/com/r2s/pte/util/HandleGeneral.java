package com.r2s.pte.util;

import java.time.LocalDateTime;
import java.util.List;

import com.r2s.pte.common.Text;
import com.r2s.pte.dto.RequestUserInfoDTO;
import com.r2s.pte.dto.TokenDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.QuestionType;

public interface HandleGeneral {

	QuestionType setQuestionTypeMatchCategrory(Category category);
	int countWord(String input);
	String normalizeString(String input);
	List<Text> setListText(String result);
	String getURL(String content);
	String parseLocalDateTime(LocalDateTime dateTime);
	LocalDateTime parseLocalDateTime(String dateTime);
	Integer randomScore();

    Long getIdUser(String response);

    Long getUserType(String response);

    TokenDTO createToken(RequestUserInfoDTO requestUserInfoDTO);
}
