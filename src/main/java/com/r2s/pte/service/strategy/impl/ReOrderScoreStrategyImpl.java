package com.r2s.pte.service.strategy.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.entity.QuestionResponseUser;
import com.r2s.pte.entity.UserScore;
import com.r2s.pte.mapper.UserScoreMapper;
import com.r2s.pte.repository.UserScoreRepository;
import com.r2s.pte.service.QuestionResponseUserService;
import com.r2s.pte.service.UserScoreService;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;
@Component("ReOrderScoreStrategy")
public class ReOrderScoreStrategyImpl implements QuestionScoreStrategy {
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private QuestionResponseUserService questionResponseUserService;
	@Autowired
	private UserScoreMapper userScoreMapper;
	@Autowired
	private UserScoreRepository userScoreRepository;
	private LocalDateTime CREATE_DATE;
	@Override
	public Integer doScore(QuestionDTO questionDTO, QuestionResponseUserDTO questionResponseUser,
			QuestionSolutionDTO dto) {
		int first = 0;
		List<QuestionSolutionDTO> questionSolutionDTOs = questionDTO.getSolutions();
		QuestionSolutionDTO questionSolutionDTO = (QuestionSolutionDTO) questionSolutionDTOs.toArray()[first];
		String result = questionSolutionDTO.getValueText();
		String response = questionResponseUser.getValueText();
		questionResponseUser.setValueText(questionDTO.getName());
		if(response.equals(result))
			return 1;
		return -1;
	}
	@Override
	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs)
	{
		List<UserScore> userScores = new ArrayList<UserScore>();
		CREATE_DATE = LocalDateTime.now();
		for (UserScoreDTO userScoreDTO : userScoreDTOs) {
			QuestionDTO questionDTO = userScoreDTO.getQuestion();
			this.userScoreService.setSolutionByQuestionId(questionDTO);
			List<QuestionResponseUserDTO> questionResponseUserDTOs = userScoreDTO.getQuestionResponseUsers();
			for (QuestionResponseUserDTO questionResponseUserDTO : questionResponseUserDTOs)
			{
				int score = doScore(questionDTO, questionResponseUserDTO, null);
				questionResponseUserDTO.setQuestionDTO(questionDTO);
				QuestionResponseUser questionResponseUserSaved = this.questionResponseUserService
						.save(questionResponseUserDTO);
				UserScore userScoreSaved = setAndSaveScore(userScoreDTO, score, questionResponseUserSaved);
				userScores.add(userScoreSaved);
			}
		}
		return this.userScoreMapper.mapToOrder(userScores);
	}
	private UserScore setAndSaveScore(UserScoreDTO userScoreDTO, Integer score,
			QuestionResponseUser questionResponseUserSaved) {
		UserScore userScore = this.userScoreMapper.mapToEntity(userScoreDTO);
		userScore.setScore(score);
		userScore.setQuestionResponseUser(questionResponseUserSaved);
		this.userScoreService.setDateTimeNow(userScore, CREATE_DATE);
		return this.userScoreRepository.save(userScore);
	}


}
