package com.r2s.pte.util;

import com.r2s.pte.common.File;

public interface FileHandle {
	void update(File file);
	void delete(String url);
	String getAttributeFile(String nameFile);
	String setURLServer(String url);
}
