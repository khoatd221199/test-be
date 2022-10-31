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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="VDT.DISCUSSION_REACTIONS")
public class DiscussionReactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "REACTIONS",nullable = false)
	private Long reactions;
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
	@JoinColumn(name = "DISCUSSION_ID")
	@JsonManagedReference
	private Discussion discussion;
	
}
