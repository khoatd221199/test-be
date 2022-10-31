package com.r2s.pte.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "VDT.USER_SCORE")
public class UserScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "COMPONENT", length = 500)
	private String component;
	@Column(name = "USER_ID")
	private Long user;
	@Column(name = "SYSTEM_SCORE")
	private Integer score;
	@Column(name = "HUMAN_SCORE")
	private Integer humanScore;
	@Column(name = "Modified_Date",columnDefinition="datetime",nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By", nullable = false)
	private Long modifiedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "Created_Date", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdDate;
	@Column(name = "Created_By", nullable = false)
	private Long createdBy;
	@ManyToOne
	@JoinColumn(name = "QUESTION_ID")
	@JsonManagedReference
	@Fetch(FetchMode.JOIN)
	private Question question;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_SOLUTION_ID")
	private QuestionSolution questionSolution;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUESTION_RESPONSE_USER_ID")
	private QuestionResponseUser questionResponseUser;

}
