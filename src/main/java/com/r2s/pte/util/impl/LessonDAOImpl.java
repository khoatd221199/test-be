package com.r2s.pte.util.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.r2s.pte.entity.Lesson;
import com.r2s.pte.util.LessonDAO;

@Repository
public class LessonDAOImpl implements LessonDAO {

	private EntityManager entityManager;

	@Autowired
	public LessonDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public List<Lesson> findByQuery(String query, int page, int limit) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try {
			Session currentSession = entityManager.unwrap(Session.class);

			Query<Lesson> theQuery = currentSession.createQuery(query, Lesson.class);

			theQuery.setFirstResult(page * limit );
			theQuery.setMaxResults(limit);
			

			lessons = theQuery.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public long countTotalRecords(String query) {
		Session currentSession = entityManager.unwrap(Session.class);
		String countQ = "Select count (*) ";
		int indexOfGroupBy = query.indexOf("GROUP BY");
		if(indexOfGroupBy > 0) {
			query = query.substring(0,indexOfGroupBy);	
		}
		countQ = countQ.concat(query);
		Query<Long> countQuery = currentSession.createQuery(countQ);
		return (Long) countQuery.uniqueResult();
	}
}
