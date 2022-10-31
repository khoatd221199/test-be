package com.r2s.pte.service.strategy.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;
@Component("SingleScoreStrategy")
public class SingleChoiceScoreStrategyImpl implements QuestionScoreStrategy{

	@Override
	public Integer doScore(QuestionDTO dto, QuestionResponseUserDTO questionResponseUser,QuestionSolutionDTO questionSolutionDTO) {
		int first = 0;
		List<QuestionSolutionDTO> questionSolutionDTOs = dto.getSolutions();
		QuestionSolutionDTO solutionDTO = (QuestionSolutionDTO) questionSolutionDTOs.toArray()[first];
		questionSolutionDTO = solutionDTO;
		String result = solutionDTO.getValueText();
		String response = questionResponseUser.getValueText().trim();
		if(result.equals(response))
		{
			return 1;
		}
			
		return -1;
	}

	@Override
	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs) {
		// TODO Auto-generated method stub
		return null;
	}

}
