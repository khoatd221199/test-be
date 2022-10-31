package com.r2s.pte.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationDTO {
	public final static int size = 20;
	private List<?> contents;
	private boolean isFirst;
	private boolean isLast;
	private long totalPages;
	private long totalItems;
	private long sizeCurrentItems;
	private int numberOfCurrentPage;
	public PaginationDTO customPagination(List<?> content,long totalItems,int page, int limmit) {
		PaginationDTO pageDTO;
		List<Object> contentInAPage = new ArrayList<Object>();
        if(content.size()==0) {
			return pageDTO = new PaginationDTO(contentInAPage, false, false,0 ,0, limmit, page);
		}
		long totalPages = 0;
		boolean first = false ;
		boolean last = false ;
		totalPages = (totalItems/limmit);
		if(totalItems%limmit>0) {
			totalPages++;
		}
		if(page == 0) {
			first = true;
		}
		if((page+1)/totalPages >= 1) {
			last = true;
		}
		if(page>=0) {
			int n = page*limmit;
			for (int i = n; i < n+limmit & i < content.size(); i++) {
				contentInAPage.add(content.get(i));
			}
		}
		pageDTO = new PaginationDTO(contentInAPage, first, last,totalPages ,totalItems, limmit, page);
		return pageDTO;
	}
	
	public PaginationDTO customPaginationLesson(List<?> content,long totalItems,int page, int limmit) {
		PaginationDTO pageDTO;
        if(content.size()==0) {
			return pageDTO = new PaginationDTO();
		}
		long totalPages = 0;
		boolean first = false ;
		boolean last = false ;
		totalPages = (totalItems/limmit);
		if(totalItems%limmit>0) {
			totalPages++;
		}
		if(page == 0) {
			first = true;
		}
		if((page+1)/totalPages >= 1) {
			last = true;
		}
		pageDTO = new PaginationDTO(content, first, last,totalPages ,totalItems, limmit, page);
		return pageDTO;
	}

	

}

