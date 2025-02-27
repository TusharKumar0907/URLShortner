package com.example.Backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.models.ClickEvent;
import com.example.Backend.models.UrlMapping;
import com.example.Backend.models.User;
import com.example.Backend.repository.ClickEventRepository;
import com.example.Backend.repository.UrlMappingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClickEventService {

    @Autowired
    private final ClickEventRepository clickEventRepository;

    @Autowired
    private final UrlMappingRepository urlMappingRepository;


    public List<ClickEvent> getClickEventsByDate(String shortUrl, LocalDateTime startTime, LocalDateTime endTime) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        List<ClickEvent>clickEvents = clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, startTime, endTime);
        return clickEvents;
    }

    public List<ClickEvent> getClickEventsByDate(User user, LocalDateTime startTime, LocalDateTime endTime) {
        List<UrlMapping>urlMappings = urlMappingRepository.findByUser(user);
        List<ClickEvent>clickEvents = clickEventRepository.findByUrlMappingAndClickDateBetween(urlMappings, startTime, endTime);
        return clickEvents;
    }

}
