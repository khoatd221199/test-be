package com.r2s.pte.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;
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
@Table(name = "VDT.QUESTION_GROUP")
public class QuestionGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 500)
	private String title;
	@Column(length = 500)
	private String description;
	@Column(name = "MEDIA_SEGMENT_FROM", length = 500)
	private String mediaSegmentFrom;
	@Column(name = "IS_EMBEDDED_QUESTIONS")
	private Boolean isEmbedded;
	@Column(name = "IS_SHUFFLE")
	private Boolean isShuffle;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "Lesson_Id")
	private Lesson lesson;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "questionGroup",cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	@JsonManagedReference(value = "Question - QuestionGroup")
	private Collection<Question> questions = new HashSet<Question>();
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(title).append(description).append(mediaSegmentFrom)
				.append(isEmbedded).append(lesson).toHashCode();
	}
}
