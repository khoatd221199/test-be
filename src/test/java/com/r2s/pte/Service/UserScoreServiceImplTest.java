package com.r2s.pte.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.r2s.pte.service.UserScoreService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserScoreServiceImplTest {
	@Autowired
	private UserScoreService userScoreService;
	
	@DisplayName("Test score for single choice")
	@Test
	void testDoScore_shouldSuccess_whenIdLessonExists()
	{
		
	}
}
