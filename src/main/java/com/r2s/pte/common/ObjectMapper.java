package com.r2s.pte.common;

import com.fasterxml.jackson.databind.DeserializationFeature;

public class ObjectMapper {
	private static com.fasterxml.jackson.databind.ObjectMapper OBJECT_MAPPER = new com.fasterxml.jackson.databind.ObjectMapper();
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// IGNORE WITH PROPERTIES UNKOWN	
	}
	public static com.fasterxml.jackson.databind.ObjectMapper getMapper()
	{
		return OBJECT_MAPPER;
	}
}
