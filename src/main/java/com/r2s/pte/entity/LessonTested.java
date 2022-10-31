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
@Table(name = "VDT.LESSON_TESTED")
public class LessonTested {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "EXAM_DATE")
	private LocalDateTime examDate;
	@Column(name = "DESCRIPTION", length = 10000)
	private String description;
	@Column(name = "Modified_Date",columnDefinition="datetime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By")
	private Long modifiedBy;
	@Column(name = "Created_Date",columnDefinition="datetime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	@Column(name = "Created_By")
	private Long createdBy;
	@Column(name = "USER_ID")
	private Long userId;
	@ManyToOne
	@JoinColumn(name = "LESSON_ID")
	private Lesson lesson;

}
