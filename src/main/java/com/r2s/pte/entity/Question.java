package com.r2s.pte.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "VDT.QUESTION")
public class Question  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 500)
	private String code;
	@Column(length = 10000)
	private String name;
	@Column(length = 1000)
	private String description;
	@Column(name = "EXPLANATION",length = 1000)
	private String explanation;
	@Column(name = "LESSON_SOURCE_MEDIA_LINK",length = 1000)
	private String lessonSourceMediaLink;
	@Column(name = "LESSON_SOURCE_IMAGE_LINK",length = 1000)
	private String lessonSourceImageLink;
	@Column(name = "ZORDER")
	private Integer order;
	@Column(name = "Modified_Date",columnDefinition="DATETIME")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By")
	private long modifiedBy;
	@Column(name = "Created_Date",columnDefinition="DATETIME")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	@Column(name = "Created_By")
	private long createdBy;
	//Question Group
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "QUESTION_GROUP_ID")
	@JsonBackReference(value = "Question - QuestionGroup")
	private QuestionGroup questionGroup;
	//Question Type
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_TYPE_ID")
	private QuestionType questionType;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "question",orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	private List<QuestionOption> questionOptions = new ArrayList<QuestionOption>();
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "question", orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	private List<QuestionSolution> questionSolutions = new ArrayList<QuestionSolution>();
}
