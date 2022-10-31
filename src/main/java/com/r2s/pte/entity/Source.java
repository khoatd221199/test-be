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
@Table(name = "VDT.SOURCE")
public class Source {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "SOURCE_NAME",length = 500)
	private String sourceName;
	@Column(name = "SOURCE_WEBSITE", length = 500)
	private String sourceWebsite;
	@Column(name = "LESSON_ORIGINAL_SOURCE_LINK",length = 500)
	private String lessonOriginalSourceLink;
	@Column(name = "ORIGINAL_SOURCE_MEDIA_LINK", length = 500)
	private String originalSourceMediaLink;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "Modified_Date",columnDefinition="datetime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By")
	private long modifiedBy;
	@Column(name = "Created_Date",columnDefinition="datetime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	@Column(name = "Created_By")
	private long createdBy;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn
	private Lesson lesson;
}
