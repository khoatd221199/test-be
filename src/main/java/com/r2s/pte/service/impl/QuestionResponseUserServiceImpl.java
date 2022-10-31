package com.r2s.pte.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.common.File;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.entity.QuestionResponseUser;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.mapper.QuestionResponseUserMapper;
import com.r2s.pte.repository.QuestionResponseUserRepository;
import com.r2s.pte.service.QuestionResponseUserService;
import com.r2s.pte.service.QuestionService;
import com.r2s.pte.util.FileHandle;

@Service
@Transactional
public class QuestionResponseUserServiceImpl implements QuestionResponseUserService {
	@Autowired
	private QuestionResponseUserRepository questionResponseUserRepository;
	@Autowired
	private QuestionResponseUserMapper questionResponseUserMapper;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private FileHandle fileHandle;
	@Autowired
	private MessageSource messageSource;

	@Override
	public QuestionResponseUser save(QuestionResponseUserDTO dto) {
		QuestionResponseUser questionResponseUser = questionResponseUserMapper.mapToEntity(dto);
		questionResponseUser.setUserId(UserContext.getId());
		setTime(questionResponseUser);
		return this.questionResponseUserRepository.save(questionResponseUser);
	}
	@Override
	public QuestionResponseUser findByQuestionId(Long id, LocalDateTime createDate)
	{
		int firstIndex = 0;
		Long userId = UserContext.getId();
		List<QuestionResponseUser> questionResponseUsers = this.questionResponseUserRepository.findByQuestionId(id,userId,createDate);
		if(questionResponseUsers != null && questionResponseUsers.size() >0)
			return questionResponseUsers.get(firstIndex);
		throw new ErrorMessageException(
				String.format(messageSource.getMessage("NotFound", null,null), "QuestionResponseUser","Id",String.valueOf(id))
				,TypeError.NotFound);
	}
	@Override
	public void deleteByQuestionId(Long id)
	{
		List<QuestionResponseUser> questionResponseUsers = this.questionResponseUserRepository.findByQuestion(id);
		if(questionResponseUsers != null && questionResponseUsers.size()>0)
		{
			questionResponseUsers.forEach(questionResponseUser->{
				questionResponseUser.setQuestion(null);
				this.questionResponseUserRepository.save(questionResponseUser);
				this.questionResponseUserRepository.deleteById(questionResponseUser.getId());
			});
		}
	}
	private void setTime(QuestionResponseUser questionResponseUser) {
		LocalDateTime dateTime = LocalDateTime.now();
		questionResponseUser.setCreatedDate(dateTime);
		questionResponseUser.setModifiedDate(dateTime);
		questionResponseUser.setCreatedBy(UserContext.getId());
		questionResponseUser.setModifiedBy(UserContext.getId());
		questionResponseUser.setUserId(UserContext.getId());
	}

	@Override
	public List<QuestionResponseUser> saveList(List<QuestionResponseUserDTO> questionResponseUserDTOs) {
		List<QuestionResponseUser> questionResponseUsers = new ArrayList<QuestionResponseUser>();
		questionResponseUserDTOs.forEach(item -> {
			questionResponseUsers.add(save(item));
		});
		return questionResponseUsers;
	}

	@Override
	public QuestionResponseUser saveBySpeech(MultipartFile speech, Long idQuestion) {
		QuestionResponseUser questionResponseUser = new QuestionResponseUser();
		questionResponseUser.setQuestion(questionService.findById(idQuestion));
		File file = new File();
		file.setFile(speech);
		this.fileHandle.update(file);
		questionResponseUser.setValueMedia(file.getFileName());
		questionResponseUser.setUserId(UserContext.getId());
		setTime(questionResponseUser);
		return this.questionResponseUserRepository.save(questionResponseUser);

	}
}
