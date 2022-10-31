package com.r2s.pte.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="VDT.VOCAB")
public class Vocab{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "VOCAB",nullable = false)
	private String vocab;
	@Column(name = "COUNTRY_ID",nullable = false)
	private Long countryId;
	@Column(name = "IPA",nullable = true,length = 500)
	private String ipa;
	@Column(name = "DEFINITION",nullable = true,length = 10000)
	private String definition;
	@Column(name = "PRIORITY")
	private Integer priority;
	@Column(name = "IS_PHRASAL_WORD")
	private Boolean isPhrasalWord;
	@Column(name = "EXAMPLE",length = 10000)
	private String example;
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
	
	@OneToMany(mappedBy = "vocab")
	@JsonBackReference
	@Fetch(FetchMode.SUBSELECT)
	private Set<VocabUser> vocabUsers ;
	
}
