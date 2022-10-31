package com.r2s.pte.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.ParamUserScoreDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.service.UserScoreService;

@RestController
@RequestMapping(value = { "api-dev/user-score", "api-pro/user-score", "api/user-score" })
public class UserScoreController {
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private MessageSource messageSource;
	private final String ENTITY = "User Score";

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody List<UserScoreDTO> userScoreDTOs) {
		ScoreResponseDTO scoreResponseDTO = userScoreService.save(userScoreDTOs);
		return ResponseEntity.status(HttpStatus.CREATED).body(scoreResponseDTO);
	}

	@GetMapping("{lessonId}/{userId}")
	public ResponseEntity<?> get(@PathVariable Long lessonId, @PathVariable Long userId) {
		return ResponseEntity.ok(userScoreService.getByLessoAndUser(lessonId, userId));
	}

	@PostMapping("smw/{lessonId}")
	public ResponseEntity<?> createSMW(@RequestBody List<UserScoreDTO> userScoreDTOs, @PathVariable Long lessonId) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(this.userScoreService.saveHighlightIncorrect(userScoreDTOs, lessonId));
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteByDateTime(@RequestParam String dateTime) {
		this.userScoreService.deleteByCreate(dateTime);
		return ResponseEntity
				.ok(String.format(this.messageSource.getMessage("deleteSuccessfully", null, null), ENTITY));
	}

	@PostMapping("param")
	public ResponseEntity<?> getByLesson(@RequestBody ParamUserScoreDTO paramUserScoreDTO) {
		return ResponseEntity.ok(this.userScoreService.getByQuestionAndUser(paramUserScoreDTO));
	}

	@PostMapping("ai-text")
	public ResponseEntity<?> saveAIText(@RequestBody UserScoreDTO userScoreDTO) {
		return ResponseEntity.ok(this.userScoreService.saveByAIText(userScoreDTO));
	}

	@PostMapping(value = "ai-speech", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@RequestPart(name = "question", required = true) String questionId,
			@RequestPart(name = "speech", required = false) MultipartFile speechFile) {
		Long id;
		try {
			id = Long.valueOf(questionId);
		} catch (Exception e) {
			throw new ErrorMessageException(messageSource.getMessage("ErrorFormat", null, null), TypeError.BadRequest);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userScoreService.saveSpeech(speechFile, id));
	}
	@PostMapping(value = "ai-speech/do-score")
	public ResponseEntity<?> aiDoScore(@RequestBody List<UserScoreDTO> userScoreDTOs)
	{
		this.userScoreService.AIDoScore(userScoreDTOs);
		return ResponseEntity.ok("Success");
	}
}
