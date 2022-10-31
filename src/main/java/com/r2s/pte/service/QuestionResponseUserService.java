package com.r2s.pte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.entity.QuestionResponseUser;

public interface QuestionResponseUserService {

	QuestionResponseUser save(QuestionResponseUserDTO dto);

	List<QuestionResponseUser> saveList(List<QuestionResponseUserDTO> questionResponseUserDTOs);

	QuestionResponseUser saveBySpeech(MultipartFile speech, Long idQuestion);

	QuestionResponseUser findByQuestionId(Long id, LocalDateTime createDate);

	void deleteByQuestionId(Long id);

}
