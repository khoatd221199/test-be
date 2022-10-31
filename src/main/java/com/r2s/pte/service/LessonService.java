package com.r2s.pte.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.dto.LessonAudioCreateDTO;
import com.r2s.pte.dto.LessonCreateDIDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.LessonCreationDTO;
import com.r2s.pte.dto.LessonDetailViewDTO;
import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.RequestLessonDTO;
import com.r2s.pte.entity.Lesson;

public interface LessonService {

	long count();

	boolean existsById(Long id);

	Lesson findById(Long id);

	LessonDetailViewDTO getDetailById(Long id);

	LessonDetailViewDTO getLessonDetailDefault();

	boolean isExists(Long id);

	PaginationDTO findAll(RequestLessonDTO requestDTO);

	PaginationDTO findByRequestLessonDTO(RequestLessonDTO requestDTO);

	LessonCreateDTO save(LessonCreateDTO entity);

	void update(long id, LessonCreateDTO dto);

	LessonCreateDIDTO readJson(String lesson, MultipartFile image);

	void save(LessonCreateDIDTO lessonCreateDIDTO);

	List<Lesson> getAllByZorderCategory(Long zOrder, Long idCategory);

	void update(LessonCreateDIDTO dto, long id);

	void active(Long id);

	String makeQueryWithRequestDAO(RequestLessonDTO requestDTO);

	boolean isInteger(String s);

	void deleteById(Long id);

	Long getByQuestion(Long questionId);

	LessonCreateDTO save(LessonAudioCreateDTO lessonAudioCreateDTO);

	String getContentByQuestionId(Long questionId);

	void disableById(Long id);

	LessonCreationDTO readJson(String lesson, MultipartFile image, MultipartFile shadowing);

	void save(LessonCreationDTO lesson);

	void deleteByIdAndCode(Long id, String code);

	void deleteByListId(List<Long> ids);

}
