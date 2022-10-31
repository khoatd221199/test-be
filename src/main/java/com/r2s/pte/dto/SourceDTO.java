package com.r2s.pte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SourceDTO {
	private long id;
	private String name;
	private String website;
	private String lessonOriginalSourceLink;
	private String originalSourceMediaLink;
	private String description;

}
