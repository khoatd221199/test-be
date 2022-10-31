package com.r2s.pte.entity;

import java.time.LocalDateTime;
import java.util.Collection;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="VDT.DISCUSSION")
public class Discussion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "PARRENT_ID",nullable = true)
	private Long parentId;
	@Column(name = "DISCUSTION_CONTENT",length = 2000,nullable = true)
	private String discussionContent;
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
	@Column(name = "IS_INCLUDE_ANSWER",nullable = true)
	private Boolean isIncludeAnswer;
	
	
	@OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@JsonIgnore
	private Collection<DiscussionReactions> discussionReactions ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LESSON_ID")
	private Lesson lesson;

}

