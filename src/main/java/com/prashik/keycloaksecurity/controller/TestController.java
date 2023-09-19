package com.prashik.keycloaksecurity.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.prashik.keycloaksecurity.model.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/anonymous")
    public ResponseEntity<Response> getAnonymous() {
        return ResponseEntity.ok(new Response("Hello Anonymous"));
    }

    @GetMapping("/admin")
    public ResponseEntity<Response> getAdmin(Principal principal) throws AccessDeniedException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("name");
        String userEmail = (String) token.getTokenAttributes().get("email");
        return ResponseEntity.ok(new Response("Hello Admin \nUser Name : " + userName + "\nUser Email : " + userEmail));
    }

    @GetMapping("/user")
    public ResponseEntity<Response> getUser(Principal principal) throws AccessDeniedException {
    	try {
    		JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
            String userName = (String) token.getTokenAttributes().get("name");
            String userEmail = (String) token.getTokenAttributes().get("email");
            return ResponseEntity.ok(new Response("Hello User \nUser Name : " + userName + "\nUser Email : " + userEmail));
		} catch (Exception e) {
			throw new AccessDeniedException("acess denied");
		}
        
    }
    
    @GetMapping("/manager")
    public ResponseEntity<Response> getManager(Principal principal) throws AccessDeniedException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("name");
        String userEmail = (String) token.getTokenAttributes().get("email");
        return ResponseEntity.ok(new Response("Hello User \nUser Name : " + userName + "\nUser Email : " + userEmail));
    }

    @GetMapping("/error")
    public void globalError(HttpServletRequest request, HttpServletResponse response) {
		throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()));
	}
}

