package com.r2s.pte.Service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.r2s.pte.dto.CategoryDTO;
import com.r2s.pte.dto.LessonCreateDTO;
import com.r2s.pte.dto.LessonDetailViewDTO;
import com.r2s.pte.dto.PaginationDTO;
import com.r2s.pte.dto.RequestLessonDTO;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonCategory;
import com.r2s.pte.entity.QuestionGroup;
import com.r2s.pte.entity.QuestionType;
import com.r2s.pte.mapper.CategoryMapper;
import com.r2s.pte.mapper.LessonMapper;
import com.r2s.pte.repository.LessonRepository;
import com.r2s.pte.service.CategoryService;
import com.r2s.pte.service.LessonCategoryService;
import com.r2s.pte.service.LessonService;
import com.r2s.pte.service.QuestionGroupService;
import com.r2s.pte.service.QuestionService;
import com.r2s.pte.util.FileHandle;
import com.r2s.pte.util.HandleGeneral;
import com.r2s.pte.util.LessonDAO;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {
	@Mock
	private LessonCategoryService lessonCategoryService;
	@Mock
	private CategoryService categoryService;
	@Mock
	private LessonMapper lessonMapper;
	@Mock
	private CategoryMapper categoryMapper;
	@Mock
	private QuestionService questionService;
	@Mock
	private HandleGeneral handleGeneral;
	@Mock
	private MessageSource messageSource;
	@Mock
	private LessonDAO lessonDAO;
	@Mock
	private QuestionGroupService questionGroupService;
	@Mock
	private FileHandle fileHandle;
	@Mock 
	private LessonRepository lessonRepository;
	@InjectMocks
	private LessonService lessonService;
	private final LessonCreateDTO lessonCreateDTORA = new LessonCreateDTO();
	private final Lesson lessonReadAloud = new Lesson();
	private final CategoryDTO categoryDTO = new CategoryDTO();
	private final Category category = new Category();
	private final List<CategoryDTO> categoryDTOList = new ArrayList<>();
	private final QuestionGroup questionGroup = new QuestionGroup();
	private final QuestionType questionType = new QuestionType();
	private final LessonCategory lessonCategory = new LessonCategory();
	@BeforeEach
	void init()
	{
		lessonCreateDTORA.setTitle("Major Conclusion");
		lessonCreateDTORA.setContent("Our major conclusion is that the current measure need stoberevised.");
		lessonCreateDTORA.setPreparationTime(40);
		lessonCreateDTORA.setDuration(40);
		categoryDTO.setId(3);
		categoryDTOList.add(categoryDTO);
		lessonCreateDTORA.setCategoryDTO(categoryDTOList);
		questionGroup.setIsEmbedded(Boolean.FALSE);
		lessonCreateDTORA.setQuestionGroup(questionGroup);
		lessonReadAloud.setTitle("Major Conclusion");
		lessonReadAloud.setContent("Our major conclusion is that the current measure need stoberevised.");
		lessonReadAloud.setPreparationTime(40);
		lessonReadAloud.setDuration(40);
		lessonReadAloud.setQuestionGroup(questionGroup);
		category.setCode("RA");
		category.setId(3);
		lessonCategory.setLesson(lessonReadAloud);
		lessonCategory.setCategory(category);
	}
	@Test
	void testSave_shouldPass_whenLessonOfReadAloud() {
		Long count = Long.valueOf(1);
		Long categoryId = Long.valueOf(3);
		Mockito.when(lessonMapper.mapToEntity(lessonCreateDTORA)).thenReturn(lessonReadAloud);
		Mockito.when(lessonRepository.count()).thenReturn(count);
		Mockito.when(lessonRepository.save(lessonReadAloud)).thenReturn(lessonReadAloud);
		Mockito.when(categoryService.findById(categoryId)).thenReturn(categoryDTO);
		Mockito.when(categoryMapper.map(categoryDTO)).thenReturn(category);
		Mockito.doNothing().when(categoryService).checkCategoryIsParent(category);
		Mockito.when(handleGeneral.setQuestionTypeMatchCategrory(category)).thenReturn(questionType);
		Mockito.when(lessonCategoryService.saveLessonCategory(lessonReadAloud,category)).thenReturn(lessonCategory);
		Mockito.doNothing().when(questionService).saveFromLessonService(lessonCreateDTORA.getQuestionDTO(),questionGroup,questionGroup.getIsEmbedded());
		Mockito.when(lessonMapper.mapToDTO(lessonReadAloud)).thenReturn(lessonCreateDTORA);

		LessonCreateDTO lessonCreateDTOActual = lessonService.save(lessonCreateDTORA);
		assertThat(lessonCreateDTOActual.getTitle()).isEqualTo(lessonCreateDTORA.getTitle());
	}
	 @DisplayName("Test for find All Lesson method")
	    @Test
	    void testFindAllLessonshouldSuccesfully() {
		 	RequestLessonDTO requestDTO = new RequestLessonDTO(null, null, null, null, null, null, null, null, null, null, null, null, 0, 10);
	        PaginationDTO actualResult = new PaginationDTO();  
	        Mockito.when(lessonService.findByRequestLessonDTO(requestDTO)).thenReturn(actualResult);
	        assertThat(actualResult).isNotNull();
	    }
	 @DisplayName("Test for get Detail Lesson by id")
	    @Test 
	    void testGetLessonDetailById() {
	        // wrong data
	        LessonDetailViewDTO detailViewDTO = new LessonDetailViewDTO();
	        Lesson lesson = new Lesson();
	        // cover the find by id
	        Mockito.when(lessonRepository.findById((long) 1)).thenReturn(Optional.of(lesson));
	        Mockito.when(lessonMapper.mapToViewDetailDTO(lesson)).thenReturn(detailViewDTO);
	        // get the actual result
	        LessonDetailViewDTO actualResult = lessonService.getDetailById((long) 1);
	        // check the service
	        assertThat(detailViewDTO).isEqualTo(actualResult);
	    }
	 
}
