package com.r2s.pte.mapper.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.entity.UserScore;
import com.r2s.pte.mapper.strategy.UserScoreMapperStrategy;

@Component("FIBMapper")
public class UserScoreFIBMapperStrategyImpl implements UserScoreMapperStrategy {

	@Override
	public ScoreResponseDTO map(List<UserScore> userScores) {
		int firstIndex = 0;
		ScoreResponseDTO dto = new ScoreResponseDTO();
		dto.setMaxScore(getMaxScore(userScores, firstIndex));
		dto.setScore(setScore(userScores));
		dto.setUserId(userScores.get(firstIndex).getUser());
		dto.setValueResponses(setValueResponse(userScores));
		dto.setCreateDate(userScores.get(firstIndex).getCreatedDate());
		return dto;
	}

	private int getMaxScore(List<UserScore> userScores, int index) {
		// set maxScore
		QuestionGroup questionGroup = userScores.get(index).getQuestion().getQuestionGroup();
		return questionGroup.getQuestions().size();

	}

	private Integer setScore(List<UserScore> userScores) {
		Integer score = 0;
		for (UserScore userScore : userScores) {
			score += userScore.getScore();
		}
		return score > 0 ? score : 0;
	}

	private List<String> setValueResponse(List<UserScore> userScores) {
		List<String> values = new ArrayList<String>();
		for (UserScore userScore : userScores) {
			values.add(userScore.getQuestionResponseUser().getValueText());
		}
		return values;
	}
}
