package com.r2s.pte.service.strategy.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;

@Component("MultipleChoiceScoreStrategy")
public class MultipleChoiceScoreStrategyImpl implements QuestionScoreStrategy {



	@Override
	public Integer doScore(QuestionDTO questionDTO, QuestionResponseUserDTO questionResponseUser,
			QuestionSolutionDTO questionSolutionDTO) {
		List<QuestionSolutionDTO> questionSolutionDTOS = questionDTO.getSolutions();

		String valueResponse = questionResponseUser.getValueText();
		boolean isCorrect = isCorrect(valueResponse, questionSolutionDTOS, questionSolutionDTO);
		if (isCorrect) {
			return 1;
		}
		return -1;
	}

	private boolean isCorrect(String questionReponse, List<QuestionSolutionDTO> questionSolutionDTOs,
			QuestionSolutionDTO questionSolution) {
		for (QuestionSolutionDTO questionSolutionDTO : questionSolutionDTOs) {
			String result = questionSolutionDTO.getValueText();
			if (questionReponse.equals(result))
			{
				questionSolution = questionSolutionDTO;
				return true;
			}
		}
		questionSolution = null;
		return false;
	}

	@Override
	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs) {
		// TODO Auto-generated method stub
		return null;
	}

}
