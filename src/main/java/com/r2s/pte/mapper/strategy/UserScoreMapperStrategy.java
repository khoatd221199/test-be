package com.r2s.pte.mapper.strategy;

import java.util.List;

import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.entity.UserScore;

public interface UserScoreMapperStrategy {

	ScoreResponseDTO map(List<UserScore> userScores);

}
