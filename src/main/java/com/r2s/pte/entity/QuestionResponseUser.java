package com.r2s.pte.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "VDT.QUESTION_RESPONSE_USER")
public class QuestionResponseUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "VALUE_TEXT", length = 1000)
	private String valueText;
	@Column(name = "VALUE_MEDIA")
	private String valueMedia;
	@Column(name = "Modified_Date",columnDefinition="DATETIME")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By")
	private Long modifiedBy;
	@Column(name = "Created_Date",columnDefinition="DATETIME")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	@Column(name = "Created_By")
	private Long createdBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID")
	private Question question;
	@Column(name = "USER_ANSWER_ID")
	private Long userId;

	
	

}
