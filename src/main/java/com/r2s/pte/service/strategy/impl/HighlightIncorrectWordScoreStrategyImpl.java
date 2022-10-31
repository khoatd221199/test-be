package com.r2s.pte.service.strategy.impl;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("HighlightIncorrectWordScore")
public class HighlightIncorrectWordScoreStrategyImpl implements QuestionScoreStrategy {
    @Override
    public Integer doScore(QuestionDTO questionDTO, QuestionResponseUserDTO questionResponseUser, QuestionSolutionDTO dto) {
        return null;
    }

	@Override
	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs) {
		// TODO Auto-generated method stub
		return null;
	}
}
