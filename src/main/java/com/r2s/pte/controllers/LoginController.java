package com.r2s.pte.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.pte.dto.RequestUserInfoDTO;
import com.r2s.pte.dto.UserContext;
import com.r2s.pte.util.HandleGeneral;
import com.r2s.pte.util.JWTUtil;

@RestController
@RequestMapping(value = {"api-dev/token", "api-pro/token", "api/token"})
public class LoginController {
    @Autowired
    private HandleGeneral handleGeneral;
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody UserContext userContext) {
		String token = JWTUtil.generate(userContext.getUserId(), userContext.getUserType());
		Map<String, String> data = new HashMap<String, String>();
		data.put("status", String.valueOf(HttpStatus.OK.value()));
		data.put("id", String.valueOf(userContext.getUserId()));
		data.put("user type", String.valueOf(userContext.getUserType()));
		data.put("token", token);
		return ResponseEntity.ok().body(data);
	}

    @PostMapping(value = "v1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody RequestUserInfoDTO requestUserInfoDTO) {
        return ResponseEntity.ok().body(handleGeneral.createToken(requestUserInfoDTO));	
    }
}
