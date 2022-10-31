package com.r2s.pte.mapper;

import java.util.List;

import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreCreateDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.entity.UserScore;

public interface UserScoreMapper {
    UserScore mapToEntity(UserScoreDTO dto);

    UserScoreDTO mapToDTO(UserScore entity);

	UserScore mapToEntity(UserScoreCreateDTO dto);

	ScoreResponseDTO mapToResponse(List<UserScore> userScores);

	ScoreResponseDTO mapToOrder(List<UserScore> userScores);

	ScoreResponseDTO mapToWFD(List<UserScore> userScores);

	ScoreResponseDTO mapToHighlightIncorrect(List<UserScore> userScores, Long lessonId);

	ScoreResponseDTO mapToAISpeech(UserScore userScore);

	ScoreResponseDTO mapToAIText(UserScore userScore);
}
