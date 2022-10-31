package com.r2s.pte.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Table(name = "VDT.LESSON")
public class Lesson  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "COUNTRY_ID",nullable = false)
	private int languageId;
	@Column(length = 80)
	private String title;
	@Column(length = 10000)
	private String content;
	@Column(length = 500)
	private String description;
	@Column(length = 10000)
	private String explanation;
	@Column(name = "INTERNAL_NOTES",length = 500)
	private String internalNotes;
	@Column(name = "PREPARATION_TIME")
	private int preparationTime;
	@Column(name = "DURATION")
	private int duration;
	private boolean shared;
	private boolean status;
	@Column(name = "LESSON_SOURCE_MEDIA_LINK_SHADOW",length = 500)
	private String lessonSourceMediaLinkShadow;
	@Column(name = "LESSON_SOURCE_MEDIA_LINK_VIDEO",length = 500)
	private String lessonSourceMediaLinkVideo;
	@Column(name = "LESSON_SOURCE_MEDIA_LINK_IMAGE",length = 500)
	private String lessonSourceMediaLinkImage;
	@Column(name = "REVIEWED_BY")
	private Long userIdAsReviewer;
	@Column(name = "APPROVED_BY")
	private Long userIdAsAppover;
	@Column(name = "ZOrder")
	private Long zOrder;
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

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "lesson", cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SUBSELECT)
	@JsonManagedReference
	@JsonIgnore
	private Collection<LessonCategory> listLesson_Category = new HashSet<LessonCategory>();
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy = "lesson",cascade = CascadeType.ALL)
	@JsonIgnore
	private QuestionGroup questionGroup;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "lesson", cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SUBSELECT)
	@JsonManagedReference
	@JsonIgnore
	private Collection<LessonMedia> lessonMedias = new HashSet<LessonMedia>();
	
	
	
}
