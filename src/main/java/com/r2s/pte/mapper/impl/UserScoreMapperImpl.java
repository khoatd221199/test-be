package com.r2s.pte.mapper.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.dto.UserScoreCreateDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.entity.Question;
import com.r2s.pte.entity.QuestionResponseUser;
import com.r2s.pte.entity.QuestionSolution;
import com.r2s.pte.entity.UserScore;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.QuestionMapper;
import com.r2s.pte.mapper.QuestionResponseUserMapper;
import com.r2s.pte.mapper.UserScoreMapper;
import com.r2s.pte.repository.QuestionResponseUserRepository;
import com.r2s.pte.repository.QuestionSolutionRepository;
import com.r2s.pte.service.QuestionService;
import com.r2s.pte.service.QuestionSolutionService;
import com.r2s.pte.util.HandleGeneral;

@Component
public class UserScoreMapperImpl implements UserScoreMapper {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionResponseUserMapper questionResponseUserMapper;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionSolutionService questionSolutionService;
	@Autowired
	private HandleGeneral handleGeneral;
	@Autowired
	private QuestionSolutionRepository questionSolutionRepository;
	@Autowired
	private QuestionResponseUserRepository questionResponseUserRepository;
	@Autowired
	private MessageSource messageSource;
	private final Integer MAX_SCORE_DEFAULT = 90;

	@Override
	public UserScore mapToEntity(UserScoreDTO dto) {
		if (dto == null)
			return null;
		UserScore entity = new UserScore();
		entity.setComponent(dto.getComponent());
		entity.setScore(dto.getScore());
		setUser(entity, dto);
		QuestionDTO questionDTO = dto.getQuestion();
		entity.setQuestion(getQuestion(questionDTO));
		return entity;
	}

	@Override
	public UserScore mapToEntity(UserScoreCreateDTO dto) {
		if (dto == null)
			return null;
		UserScore entity = new UserScore();
		entity.setComponent(dto.getComponent());
		entity.setId(dto.getUser());
		return entity;

	}

	@Override
	public UserScoreDTO mapToDTO(UserScore entity) {
		if (entity == null)
			return null;
		UserScoreDTO dto = new UserScoreDTO();
		dto.setId(entity.getId());
		dto.setComponent(entity.getComponent());
		if(entity.getScore()!= null) {
			dto.setScore(entity.getScore());
		}
		dto.setUser(entity.getUser());
		Question question = entity.getQuestion();
		setQuestionDTO(question, dto);
		return dto;
	}

	private Question getQuestion(QuestionDTO questionDTO) {

		Long idQuestion = questionDTO.getId();
		if (idQuestion == 0)
			return null;
		Question question = this.questionService.findById(idQuestion);
		return question;
	}

	void setQuestionSolution(QuestionSolutionDTO questionSolutionDTO, UserScore entity) {
		Long idSolution = questionSolutionDTO.getId();
		QuestionSolution questionSolution = this.questionSolutionService.findById(idSolution);
		entity.setQuestionSolution(questionSolution);
	}

	void setQuestionResponseUser(QuestionResponseUserDTO questionResponseUserDTO, UserScore entity) {
		if (questionResponseUserDTO != null) {
			QuestionResponseUser questionResponseUser = questionResponseUserMapper.mapToEntity(questionResponseUserDTO);
			entity.setQuestionResponseUser(questionResponseUser);
		}
	}

	void setQuestionDTO(Question question, UserScoreDTO dto) {
		if (question != null) {
			QuestionDTO questionDTO = questionMapper.mapToDTO(question);
			dto.setQuestion(questionDTO);
		}
	}

	@Override
	public ScoreResponseDTO mapToResponse(List<UserScore> userScores) {
		int first = 0;
		ScoreResponseDTO dto = new ScoreResponseDTO();
		dto.setMaxScore(getMaxScore(userScores, first));
		dto.setScore(setScore(userScores));
		dto.setUserId(userScores.get(first).getUser());
		dto.setValueResponses(setValueResponse(userScores));
		dto.setCreateDate(userScores.get(first).getCreatedDate());
		return dto;
	}

	private Integer setScore(List<UserScore> userScores) {
		Integer score = 0;
		for (UserScore userScore : userScores) {
				if(userScore.getScore()!=null) {
					score += userScore.getScore();
				}
		}
		return score > 0 ? score : 0;
	}

	@Override
	public ScoreResponseDTO mapToOrder(List<UserScore> userScores) {
		int first = 0;
		ScoreResponseDTO dto = new ScoreResponseDTO();
		UserScore userScore = userScores.get(first);
		dto.setMaxScore(setMaxScoreForOrder(userScores));
		dto.setScore(setScoreOrder(userScores));
		dto.setUserId(userScore.getUser());
		dto.setValueResponses(setValueResponse(userScores));
		dto.setCreateDate(userScores.get(first).getCreatedDate());
		return dto;
	}

	@Override
	public ScoreResponseDTO mapToWFD(List<UserScore> userScores) {
		ScoreResponseDTO dto = new ScoreResponseDTO();
		int first = 0;
		UserScore userScore = userScores.get(first);
		String valueText = userScore.getQuestionSolution().getValueText();
		if (valueText == null)
			valueText = userScore.getQuestionSolution().getExplanation();
		dto.setMaxScore(this.handleGeneral.countWord(valueText));
		if(userScore.getScore()!=null) {
			dto.setScore(userScore.getScore());
		}
		dto.setValueResponses(setValueResponse(userScores));
		dto.setCreateDate(userScores.get(first).getCreatedDate());
		dto.setUserId(userScore.getUser());
		return dto;
	}

	@Override
	public ScoreResponseDTO mapToHighlightIncorrect(List<UserScore> userScores, Long lessonId) {
		ScoreResponseDTO dto = new ScoreResponseDTO();
		UserScore userScore = userScores.get(0);
		dto.setScore(setScore(userScores));
		dto.setValueResponses(setValueResponse(userScores));
		dto.setUserId(userScore.getUser());
		// set max value
		dto.setMaxScore(getMaxScoreSMW(userScores, lessonId));
		return dto;
	}

	private Integer setScoreOrder(List<UserScore> userScores) {
		Integer score = 0;
		for (int i = 0; i < userScores.size(); i++) {
			try {
				UserScore userScore = userScores.get(i);
				UserScore userScoreNext = userScores.get(i + 1);
				Integer scoreCurrent = userScore.getScore();
				Integer scoreNext = userScoreNext.getScore();
				int resultTrue = 1;
				if (scoreCurrent == scoreNext && scoreCurrent == resultTrue)
					score++;
				else {
					// check answer continously
					Question questionCurrent = userScore.getQuestion();
					Question questionNext = userScoreNext.getQuestion();
					score += checkAnswerContinously(questionCurrent, questionNext);

				}
			} catch (IndexOutOfBoundsException ex) {
				break;
			}
		}
		return score > 0 ? score : 0;
	}

	private int setMaxScoreForOrder(List<UserScore> userScores) {
		return userScores.size() - 1;
	}

	private int getMaxScore(List<UserScore> userScores, int index) {
		// set maxScore
		Long idLesson = userScores.get(index).getQuestion().getQuestionGroup().getLesson().getId();
		int max = this.questionSolutionService.countByLesson(idLesson);
		return max;
	}

	private int getMaxScoreSMW(List<UserScore> userScores, Long lessonId) {
		int maxScore = this.questionSolutionRepository.countQuestionSoltutionByLessonId(lessonId);
		return maxScore;

	}

	private List<String> setValueResponse(List<UserScore> userScores) {
		List<Long> listId = new ArrayList<Long>();
		for (UserScore userScore : userScores) {
			listId.add(userScore.getId());
		}
		return this.questionResponseUserRepository.getValueTextByUserScore(listId);
	}

	private Integer checkAnswerContinously(Question questionCurrent, Question questionNext) {
		String codeCurrent = questionCurrent.getCode();
		String codeNext = questionNext.getCode();
		int index = 1; // for example: #1#
		int numberOfCodeCurrent = Integer.valueOf(codeCurrent.substring(index, index + 1));
		int numberOfCodeNext = Integer.valueOf(codeNext.substring(index, index + 1));
		if ((numberOfCodeCurrent + 1) == numberOfCodeNext)
			return 1;
		return 0;
	}

	private void setUser(UserScore userScore, UserScoreDTO userScoreDTO) {
		Long user = UserContext.getId();
		if (user != null && user.longValue() != 0) {
			userScore.setUser(user);
		} else {
			throw new ErrorMessageException(this.messageSource.getMessage("Forbidden", null, null),
					TypeError.Forbidden);
		}
	}

	@Override
	public ScoreResponseDTO mapToAIText(UserScore userScore) {
		ScoreResponseDTO scoreResponseDTO = setProperties(userScore);
		String valueText = userScore.getQuestionResponseUser().getValueText();
		scoreResponseDTO.setValueResponses(Arrays.asList(valueText));
		return scoreResponseDTO;
	}

	@Override
	public ScoreResponseDTO mapToAISpeech(UserScore userScore) {
		ScoreResponseDTO scoreResponseDTO = setProperties(userScore);
		String valueMedia = userScore.getQuestionResponseUser().getValueMedia();
		scoreResponseDTO.setMedia(valueMedia);
		return scoreResponseDTO;
	}

	private ScoreResponseDTO setProperties(UserScore userScore) {
		ScoreResponseDTO scoreResponseDTO = new ScoreResponseDTO();
		scoreResponseDTO.setMaxScore(MAX_SCORE_DEFAULT);
		scoreResponseDTO.setUserId(userScore.getUser());
		scoreResponseDTO.setCreateDate(userScore.getCreatedDate());
		return scoreResponseDTO;
	}
}
