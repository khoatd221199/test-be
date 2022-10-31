package com.r2s.pte.service.strategy;

import java.util.List;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;

public interface QuestionScoreStrategy {
    Integer doScore(QuestionDTO questionDTO, QuestionResponseUserDTO questionResponseUser, QuestionSolutionDTO dto);

	ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs);
}
