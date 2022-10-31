package com.r2s.pte.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="VDT.BOOK_LESSON")
public class BookLesson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "QUESTION_SOLUTION_ID",nullable = false)
	private Long questionSolutionId;
	@Column(name = "Modified_Date",columnDefinition="datetime",nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By",nullable = false)
	private Long modifiedBy;
	@Column(name = "Created_Date",columnDefinition="datetime",nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	@Column(name = "Created_By",nullable = false)
	private Long createdBy;
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "LESSON_ID")
	private Lesson lesson;
	
	@ManyToOne
	@JoinColumn(name = "QUESTION_GROUP_ID")
	private QuestionGroup questionGroupId;
	
	@ManyToOne
	@JoinColumn(name = "QUESTION_ID")
	private Question questionId;
	
	@ManyToOne
	@JoinColumn(name = "QUESTION_OPTION_ID")
	private QuestionOption questionOptionId;
	
//	@ManyToOne
//	@JoinColumn(name = "QUESTION_SOLUTION_ID")
//	private QuestionSolution question_SolutionId;
	
}
