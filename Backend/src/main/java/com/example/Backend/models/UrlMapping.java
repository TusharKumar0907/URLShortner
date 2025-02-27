package com.example.Backend.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "urlMappings")
public class UrlMapping {
    
    @Id
    private ObjectId id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount = 0;
    private LocalDateTime createdDate;

    @DBRef
    private User user;

}
