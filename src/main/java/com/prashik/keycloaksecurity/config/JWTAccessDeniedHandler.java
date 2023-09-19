package com.prashik.keycloaksecurity.config;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashik.keycloaksecurity.model.Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				
		Response  apiError = new Response("You don't have access to this resource contact your administrator");
		
		OutputStream os = response.getOutputStream();
		ObjectMapper om = new ObjectMapper();
		om.writeValue(os, apiError);
		os.flush();
		
	}
	
}
