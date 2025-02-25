package com.example.Backend.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.models.UrlMapping;
import com.example.Backend.models.User;
import com.example.Backend.repository.UrlMappingRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UrlMappingService {

    @Autowired
    private final UrlMappingRepository urlMappingRepository;

    public UrlMapping createShortUrl(String originalUrl, User user) {
        
        UrlMapping urlMapping = new UrlMapping();
        
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setUser(user);

        String shortUrl = randomString(8);

        urlMapping.setShortUrl(shortUrl);
        urlMapping.setCreatedDate(LocalDateTime.now());

        urlMappingRepository.save(urlMapping);

        return urlMapping;
        
    }


    public String randomString(int len) {

        String CHARACTERS = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        SecureRandom RANDOM = new SecureRandom();

        StringBuilder result = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            result.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        String check = result.toString();


        while(urlMappingRepository.existsByShortUrl(check)) {
            check = randomString(len);
        }


        return check;
        
    }
    
}
