package com.r2s.pte.util.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.pte.common.File;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.config.CloudVardyConfig;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.util.FileHandle;
import com.r2s.pte.util.HandleGeneral;

@Component
public class FileHandleImpl implements FileHandle {
	@Autowired
	private ServletContext application;
	@Autowired
	private CloudVardyConfig cloudVardyConfig;
	@Autowired
	private HandleGeneral handleGeneral;
	@Autowired
	private MessageSource messageSource;
	private final String NAME_FILE = "pte";
	private String attribute ;
	@Override
	public void update(File file) {
		try {
			Object response = this.cloudVardyConfig.post(file.getFile());
			String responseStr = response.toString();
			String url = handleGeneral.getURL(responseStr);
			file.setFileName(url);
		} catch (IOException e) {
			throw new ErrorMessageException(this.messageSource.getMessage("UploadFileFailed", null, null),
					TypeError.InternalServerError);
		}
	}
	

	@Override
	public void delete(String url) {
		java.io.File file = new java.io.File(application.getRealPath("/") + url);
		if (file.exists())
			file.delete();
	}

	@Override
	public String getAttributeFile(String nameFile) {
		int indexDot = nameFile.lastIndexOf(".");
		String attribute = nameFile.substring(indexDot);
		return attribute;
	}
	
	@Override
	public String setURLServer(String url)
	{
		//Download from URL
		String nameFileDownload = downloadFileUrl(url);
		//UPLOAD file on SERVER
		String urlServer = upload(nameFileDownload);
				return urlServer;
	}
	private String downloadFileUrl(String url) {
		attribute = getAttributeFile(url);
		String file = application.getRealPath("/") + NAME_FILE + attribute;
		try {
			URL uRL = new URL(url);
			URLConnection urlConnection;
			urlConnection = uRL.openConnection();
			urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			InputStream in = urlConnection.getInputStream();
			Files.copy(in, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	private String upload(String urlSystem) {
		java.io.File fileSystem = new java.io.File(urlSystem);
		try {
			FileInputStream fileInputStream = new FileInputStream(fileSystem);
			String name = NAME_FILE +attribute;
			String originalFileName = NAME_FILE + attribute;
			String contentType = "text/plain";
			MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType,
					IOUtils.toByteArray(fileInputStream));
			com.r2s.pte.common.File uploader = new com.r2s.pte.common.File();
			uploader.setFile(multipartFile);
			this.update(uploader);
			return uploader.getFileName();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorMessageException(this.messageSource.getMessage("UploadFileFailed", null, null),
						TypeError.BadRequest);
		}
	}

}
