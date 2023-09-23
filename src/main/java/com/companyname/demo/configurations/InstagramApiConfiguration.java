package com.companyname.demo.configurations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "instagram-api")
public class InstagramApiConfiguration {
    //attribute names must be same as prefixes in conf. file
    //(spring knows how to match our camel case name with names in conf. file)
    String getAllPostsURL;
    String authorizationToken;
    Integer version;
    String contentType;
}
