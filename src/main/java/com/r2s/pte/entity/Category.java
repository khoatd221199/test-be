package com.r2s.pte.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="VDT.CATEGORY")
public class Category  implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "GROUP_CATEGORY", length = 100,nullable = true)
	private String groupCategory;
	@Column(name = "PARENT_CODE",nullable = true,length = 100)
	private String parentCode;
	@Column(length = 500)
	private String code;
	@Column(length = 1000)
	private String name;
	@Column(length = 1000)
	private String description;
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
	
}
