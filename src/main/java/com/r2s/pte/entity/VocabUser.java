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
@Table(name="VDT.VOCAB_USER")
public class VocabUser{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "USER_ID",nullable = false)
	private Long userId;
	@Column(name = "DEFINITION_USER")
	private String definitionUser;
	@Column(name = "PRIORITY")
	private Integer priority;
	@Column(name = "EXAMPLE_USER")
	private String exampleUser;
	@Column(name = "REVISED_COUNT")
	private Integer revisedCount;
	@Column(name = "STATUS")
	private Boolean status;
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
	@JoinColumn(name = "VOCAB_ID")
	@JsonManagedReference
	private Vocab vocab;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	@JsonManagedReference
	private Category category;
	
	
}
