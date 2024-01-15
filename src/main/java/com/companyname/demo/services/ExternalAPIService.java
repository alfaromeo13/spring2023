package com.companyname.demo.services;

import com.companyname.demo.dto.CreatePostDTO;
import com.companyname.demo.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalAPIService {

    private final RestTemplate restTemplate;

    //@Value("${external-placeholder-api.posts-base-api}")
    private String basePostURL;

    public List<PostDTO> getAllPosts() {
        try {
            //for Lists,Maps and complex types we must add this line of code
            ParameterizedTypeReference<List<PostDTO>> typeRef = new ParameterizedTypeReference<>() {
            };
            //if we expected just one object at the end we would pass PostDTO.class and wouldn't need type reference
            ResponseEntity<List<PostDTO>> response =
                    restTemplate.exchange(basePostURL, HttpMethod.GET, null, typeRef);
            log.info("Getting response body: {} ", response.getBody());
            log.info("Getting response code: {} ", response.getStatusCode());

            return response.getBody();
        } catch (Exception e) {
            log.error("Error message: {} ", e.getMessage());
        }
        return Collections.emptyList();
    }

    public PostDTO getPostById(Integer id) {
        String url = basePostURL + "/" + id;
        try {
            //if we expected just one object at the end we would pass PostDTO.class and wouldn't need type reference
            ResponseEntity<PostDTO> response =
                    restTemplate.exchange(url, HttpMethod.GET, null, PostDTO.class);
            log.info("Getting response body: {} ", response.getBody());
            log.info("Getting response code: {} ", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("Error message: {} ", e.getMessage());
            return null;
        }
    }

    public PostDTO create(CreatePostDTO post) {
        try {
            //with this structure we send headers and body data (it contains req. body & req. header)
            HttpEntity<CreatePostDTO> entity = new HttpEntity<>(post, generateContentTypeHeaders());
            ResponseEntity<PostDTO> response =
                    restTemplate.exchange(basePostURL, HttpMethod.POST, entity, PostDTO.class);
            log.info("Getting response body: {} ", response.getBody());
            log.info("Getting response code: {} ", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("Error while creating new post. Error message : {} ", e.getMessage());
            return null;
        }

    }

    private HttpHeaders generateContentTypeHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        //manual way:: httpHeaders.set("Content-type","application/json; charset=UTF-8");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);//shorter way
        return httpHeaders;
    }
}