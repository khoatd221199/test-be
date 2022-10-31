package com.r2s.pte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.dto.ParamUserScoreDTO;
import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.entity.UserScore;

public interface UserScoreService {

    List<ScoreResponseDTO> getByLessoAndUser(Long lessonId, Long userId);

    ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs);

	ScoreResponseDTO saveHighlightIncorrect(List<UserScoreDTO> userScoreDTOs, Long id);

	void setSolutionByQuestionId(QuestionDTO questionDTO);

	void setDateTimeNow(UserScore userScore, LocalDateTime createDate);

	void deleteByCreate(String dateTime);

	List<ScoreResponseDTO> getByQuestionAndUser(ParamUserScoreDTO paramUserScoreDTO);

	void deleteByQuestionId(Long questionId);

    ScoreResponseDTO saveByAIText(UserScoreDTO userScoreDTO);

    ScoreResponseDTO saveSpeech(MultipartFile speech, Long questionId);

	void AIDoScore(List<UserScoreDTO> userScoreDTOs);
}
