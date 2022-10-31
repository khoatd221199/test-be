package com.r2s.pte.Service;

import com.r2s.pte.dto.LessonTestedDTO;
import com.r2s.pte.entity.Lesson;
import com.r2s.pte.entity.LessonTested;
import com.r2s.pte.mapper.LessonTestedMapper;
import com.r2s.pte.repository.LessonTestedRepository;
import com.r2s.pte.service.LessonTestedService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
@ExtendWith(MockitoExtension.class)
public class LessonTestedImplTest {
    @Mock
    private LessonTestedRepository lessonTestedRepository;
    @Mock
    private LessonTestedMapper lessonTestedMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private LessonTestedService lessonTestedService;
    private final List<LessonTestedDTO> lessonTestedDTOExpects = new ArrayList<>();
    private final List<LessonTested> lessonTesteds = new ArrayList<>();
    private final Long lessonId = Long.valueOf(1);
    @BeforeEach
    void init()
    {
        LessonTestedDTO lessonTestedDTO = new LessonTestedDTO();
        lessonTestedDTO.setLessonId(Long.valueOf(1));
        lessonTestedDTO.setUserId(Long.valueOf(1));
        lessonTestedDTO.setDescription("Description");
        lessonTestedDTOExpects.add(lessonTestedDTO);
        LessonTested lessonTested = new LessonTested();
        lessonTested.setDescription("Description");
        lessonTested.setExamDate(LocalDateTime.now());
        Lesson lesson = new Lesson(); lesson.setId(Long.valueOf(1)); lesson.setTitle("title");
        lessonTested.setLesson(lesson);
        lessonTesteds.add(lessonTested);
    }

    @Test
    @DisplayName("Test Find By Id")
    void testFindById_shouldPass_whenIdExists()
    {
        LessonTestedDTO lessonTestedDTO = lessonTestedDTOExpects.get(0);
        LessonTested lessonTested = lessonTesteds.get(0);
        Long lessonTestedId = Long.valueOf(1);
        Mockito.when(lessonTestedRepository.findById(lessonTestedId)).thenReturn(Optional.of(lessonTested));
        Mockito.when(lessonTestedMapper.mapToDTO(lessonTested)).thenReturn(lessonTestedDTO);
        LessonTestedDTO lessonTestedDTOSaved = this.lessonTestedService.findById(lessonTestedId);
        assertThat(lessonTestedDTOSaved.getDescription()).isEqualTo(lessonTestedDTO.getDescription());
    }
    @Test
    @DisplayName("Test save")
    void testSave_shouldPass_whenFieldEnough()
    {
        LessonTestedDTO lessonTestedDTO = lessonTestedDTOExpects.get(0);
        LessonTested lessonTested = lessonTesteds.get(0);
        Mockito.when(lessonTestedMapper.mapToEntity(lessonTestedDTO)).thenReturn(lessonTested);
        Mockito.when(lessonTestedRepository.save(lessonTested)).thenReturn(lessonTested);
        Mockito.when(lessonTestedMapper.mapToDTO(lessonTested)).thenReturn(lessonTestedDTO);
        LessonTestedDTO lessonTestedDTOSaved = this.lessonTestedService.save(lessonTestedDTO);
        assertThat(lessonTestedDTOSaved.getDescription()).isEqualTo(lessonTestedDTO.getDescription());
    }
    @Test
    @DisplayName("Test get lesson and user")
    void testGetLessonAndUser_shouldPass_whenLessonAndUserExists()
    {
        Long userId = Long.valueOf(1);
        Mockito.when(lessonTestedRepository.getByLessonAndUser(lessonId,userId)).thenReturn(lessonTesteds);
        LessonTestedDTO lessonTestedDTO = lessonTestedDTOExpects.get(0);
        LessonTested lessonTested = lessonTesteds.get(0);
        Mockito.when(lessonTestedMapper.mapToDTO(lessonTested)).thenReturn(lessonTestedDTO);
        List<LessonTestedDTO> lessonTestedDTOActuals = lessonTestedService.getLessonAndUser(lessonId,userId);
        LessonTestedDTO lessonTestedDTOActual = lessonTestedDTOActuals.get(0);
        assertThat(lessonTestedDTO.getUserId()).isEqualTo(lessonTestedDTOActual.getUserId());
    }
    @Test
    @DisplayName("Test count by lesson")
    void testCountByLesson_shouldPass_whenLessonExists()
    {
        Long countExcept = Long.valueOf(5);
        Mockito.when(lessonTestedRepository.coutByLessonId(lessonId)).thenReturn(countExcept);
        Long countActual = lessonTestedService.countByLesson(lessonId);
        assertThat(countActual).isEqualTo(countExcept);
    }
    @Test
    @DisplayName("Test get tested by lesson")
    void testGetTestedByLessonId_shouldPass_whenLessonExists()
    {
        Mockito.when(lessonTestedRepository.findByLessonId(lessonId)).thenReturn(lessonTesteds);
        LessonTestedDTO lessonTestedDTO = lessonTestedDTOExpects.get(0);
        LessonTested lessonTested = lessonTesteds.get(0);
        Mockito.when(lessonTestedMapper.mapToDTO(lessonTested)).thenReturn(lessonTestedDTO);
        List<LessonTestedDTO> lessonTestedDTOActuals = lessonTestedService.getTestedByLessonId(lessonId);
        assertThat(lessonTestedDTOActuals.size()).isEqualTo(lessonTesteds.size());
    }
    @Test
    @DisplayName("Test check exists id")
    void testExistsById_shouldPass_whenIdExists()
    {
        Long lessonTestedId = Long.valueOf(1);
        boolean expect = true;
        Mockito.when(lessonTestedRepository.existsById(lessonTestedId)).thenReturn(expect);
        boolean actual = lessonTestedService.existsById(lessonTestedId);
        assertThat(actual).isEqualTo(expect);
    }

}
