package com.r2s.pte.entity;

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
@Table(name="VDT.USER_TARGET")
public class UserTarget {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "USER_ID",nullable = false)
	private Long userId;
	@Column(name = "LISTENING",nullable = false)
	private int listening;
	@Column(name = "READING",nullable = false)
	private int reading;
	@Column(name = "SPEAKING",nullable = false)
	private int speaking;
	@Column(name = "WRITING",nullable = false)
	private int writing;
	@Column(name = "TARGET_DATE",columnDefinition="datetime",nullable = true)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime targetDate;
	@Column(name = "Modified_Date",columnDefinition="datetime",nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	@Column(name = "Modified_By",nullable = false)
	private long modifiedBy;
	@Column(name = "Created_Date",columnDefinition="datetime",nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	@Column(name = "Created_By",nullable = false)
	private long createdBy;
}
