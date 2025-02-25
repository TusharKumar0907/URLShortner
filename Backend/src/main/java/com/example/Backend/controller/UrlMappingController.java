package com.example.Backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.ShortUrlRequest;
import com.example.Backend.models.UrlMapping;
import com.example.Backend.models.User;
import com.example.Backend.service.UrlMappingService;
import com.example.Backend.service.UserService;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {

    @Autowired
    private UrlMappingService urlMappingService;
    @Autowired
    private UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createShortUrl(@RequestBody ShortUrlRequest shortUrlRequest, Principal principal) {
        String originalUrl = shortUrlRequest.getOriginalUrl();
        User user = userService.findByUsername(principal.getName());
        UrlMapping urlMapping = urlMappingService.createShortUrl(originalUrl, user);
        return new ResponseEntity<>(urlMapping, HttpStatus.OK);
    }
        
}
