package com.companyname.demo.services;

import com.companyname.demo.configurations.InstagramApiConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class TwitterApiService {
    //way one,we inject value from configuration file directly
    @Value("${twitter-api.get-all-tweets-url}")
    private String getTweetsUrl;

    @Value("${twitter-api.authorization-token}")
    private String authToken;

    @Value("${twitter-api.version}")
    private Integer v;

    //way two. if we have many attributes in file we use configuration class
    @Autowired
    private InstagramApiConfiguration instagramApiConfiguration;

    public List<String> getAllTweets() {
        log.info("tweets url -> {}", getTweetsUrl);
        log.info("token -> {}", authToken);
        log.info("version -> {}", v);
        return Arrays.asList("tweet1", "tweet2", "tweet3");// returning dummy values
    }

    //for testing purposes
    public void getAllInstagramPosts() {
//        log.info("instagram url -> {}", instagramApiConfiguration.getGetAllPostsURL());
//        log.info("token -> {}", instagramApiConfiguration.getAuthorizationToken());
//        log.info("version -> {}", instagramApiConfiguration.getVersion());
//        log.info("content type -> {}", instagramApiConfiguration.getContentType());
        log.info("{}", instagramApiConfiguration);
    }
}