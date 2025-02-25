package com.example.Backend.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.models.UrlMapping;
import com.example.Backend.models.User;

@Repository
public interface UrlMappingRepository extends MongoRepository<UrlMapping, ObjectId> {
    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping> findByUser(User user);
    boolean existsByShortUrl(String shortUrl); 
}

