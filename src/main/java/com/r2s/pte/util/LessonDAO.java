package com.r2s.pte.util;

import java.util.List;

import com.r2s.pte.entity.Lesson;

public interface LessonDAO {
	public List<Lesson> findByQuery(String query,int page, int limit);
	public long countTotalRecords(String query);
}
