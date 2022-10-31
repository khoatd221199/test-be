package com.r2s.pte.config;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.reactive.function.client.WebClientException;

import com.r2s.pte.common.TypeError;
import com.r2s.pte.exception.ErrorMessageException;

@Service
public class CloudVardyConfig {
	private final WebClient webClient;
	private final String URL = "https://protocol.vardytests.com";
	private final String URI_SAVE_FILE = "/api/cdn/save-file";
	private final String URI_USER_INFO = "/api/users/";

	public CloudVardyConfig(WebClient.Builder build) {
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(URL);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		this.webClient = build.baseUrl(URL).uriBuilderFactory(factory).build();
	}

	public Object post(MultipartFile file) throws IOException {
		return this.webClient.post().uri(URI_SAVE_FILE).contentType(MediaType.MULTIPART_FORM_DATA)
				.body(BodyInserters.fromMultipartData(fromFile(file))).retrieve().bodyToFlux(Object.class).blockFirst();
	}

	public Object getUser(String username, String brandUrl, String authKey) {
		try {
			return this.webClient.get()
					.uri(uriBuilder -> uriBuilder.path(URI_USER_INFO + "/" + username).queryParam("brandUrl", "{brandUrl}")
							.queryParam("authKey", authKey).build(brandUrl))
					.retrieve().bodyToMono(Object.class).block();
		}catch (WebClientException ex) {
			throw new ErrorMessageException(ex.getMessage(),TypeError.BadRequest);
		}
		

	}

	private MultiValueMap<String, HttpEntity<?>> fromFile(MultipartFile file) throws IOException {
		String keyFile = "file";
		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part(keyFile, file.getResource());
		return builder.build();
	}

}
