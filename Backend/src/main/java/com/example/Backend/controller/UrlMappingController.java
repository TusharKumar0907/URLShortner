package com.example.Backend.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.ShortUrlRequest;
import com.example.Backend.models.ClickEvent;
import com.example.Backend.models.UrlMapping;
import com.example.Backend.models.User;
import com.example.Backend.service.ClickEventService;
import com.example.Backend.service.UrlMappingService;
import com.example.Backend.service.UserService;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {

    @Autowired
    private ClickEventService clickEventService;
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

    @PostMapping("/myurls")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());        
        List<UrlMapping>urls = urlMappingService.findUrls(user);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUrlAnalytics(@PathVariable String shortUrl, @RequestParam String startDate, @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        startDate += "T00:00:00";
        endDate += "T00:00:00";
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
        List<ClickEvent>clickEvents = clickEventService.getClickEventsByDate(shortUrl, startDateTime, endDateTime);
        return new ResponseEntity<>(clickEvents, HttpStatus.OK);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getTotalClicksByDate(Principal principal, @RequestParam String startDate, @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        startDate += "T00:00:00";
        endDate += "T00:00:00";
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
        User user = userService.findByUsername(principal.getName());
        List<ClickEvent>clickEvents = clickEventService.getClickEventsByDate(user, startDateTime, endDateTime);
        int totalClicks = clickEvents.size();
        return new ResponseEntity<>(totalClicks, HttpStatus.OK);
    }
    
}
