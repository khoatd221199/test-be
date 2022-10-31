package com.r2s.pte.mapper.strategy.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.entity.UserScore;
import com.r2s.pte.mapper.strategy.UserScoreMapperStrategy;

@Component("AISpeechMapper")
public class UserScoreAISpeechMapperStrategyImpl implements UserScoreMapperStrategy{
	private final Integer MAX_SCORE_DEFAULT = 90;
	@Override
	public ScoreResponseDTO map(List<UserScore> userScores) {
		int first = 0;
		ScoreResponseDTO dto = new ScoreResponseDTO();
		dto.setMaxScore(MAX_SCORE_DEFAULT);
		dto.setScore(setScore(userScores));
		dto.setMedia(userScores.get(first).getQuestionResponseUser().getValueMedia());
		dto.setUserId(userScores.get(first).getUser());
		dto.setCreateDate(userScores.get(first).getCreatedDate());
		return dto;
	}
	private Integer setScore(List<UserScore> userScores) {
		Integer score = 0;
		for (UserScore userScore : userScores) {
			if (userScore.getScore() != null)
				score += userScore.getScore();
			else
				return null;
		}
		return score;
	}

}
