package com.r2s.pte.service.strategy.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;
@Component("FillInTheBlankScore")
public class FillInTheBlankScoreStrategyImpl implements QuestionScoreStrategy {

	@Override
	public Integer doScore(QuestionDTO questionDTO, QuestionResponseUserDTO questionResponseUser,
			QuestionSolutionDTO dto) {
		int first = 0;
		List<QuestionSolutionDTO> questionSolutionDTOS = questionDTO.getSolutions();
		QuestionSolutionDTO questionSolutionDTO = questionSolutionDTOS.get(first);
		String result = questionSolutionDTO.getValueText().trim().toLowerCase();
		String response = questionResponseUser.getValueText().trim().toLowerCase();
		if(result.equals(response))
			return 1;
		return 0;
	}

	@Override
	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs) {
		// TODO Auto-generated method stub
		return null;
	}
}
