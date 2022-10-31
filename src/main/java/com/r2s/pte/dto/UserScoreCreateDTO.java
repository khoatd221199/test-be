package com.r2s.pte.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserScoreCreateDTO {
	private String component;
	private Long user;
    private QuestionDTO questions;
    private List<QuestionResponseUserDTO> questionResponseUsers;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
}
