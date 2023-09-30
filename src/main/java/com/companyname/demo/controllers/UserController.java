package com.companyname.demo.controllers;

import com.companyname.demo.dto.UserCreateDTO;
import com.companyname.demo.dto.UserDTO;
import com.companyname.demo.dto.UserUpdateDTO;
import com.companyname.demo.projections.UserFirstAndLastNameProjection;
import com.companyname.demo.search.UserSearchFilter;
import com.companyname.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("hello")
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok("Hello message.");
        //return ResponseEntity.notFound().build();
    }

    @GetMapping("details")
    public ResponseEntity<Void> printDetails() {
        userService.printAllDetails();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getById(id);
        return user.map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //~ service which returns all users whose address starts with some string
    //http://localhost:8080/api/users/by-address-name?address=preko
    @GetMapping("by-address-name") //since address name isn't unique identifier we use query param
    public ResponseEntity<List<UserDTO>> getByAddressStartingWith(@RequestParam String address) {
        // by default @RequestParam(required = true).With required=false if value isn't passed
        // the default value will be null(this is only good for search queries for multiple cases)
        log.info("Query param : {} ", address);
        return new ResponseEntity<>(userService.findByAddressStartingWith(address), HttpStatus.OK);
    }

    //if we have more than 3 query params (ex. filter website which has 15 queries ) ...
    // (also in this way we limit number of query params that will be mapped in our RAM
    // other query names from those in filter class will be ignored and not mapped in memory)
    @GetMapping("search")//http://localhost:8080/api/users/search?name=preko&ageFrom=22&alfa=beta
    public ResponseEntity<List<UserDTO>> filterSearch(UserSearchFilter searchFilter) {
        log.info("{}", searchFilter);
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @PostMapping //we map incoming request body to our class
    public ResponseEntity<Void> create(@RequestBody UserCreateDTO userCreateDTO) {
        //we return nothing, or we return new user with newly assigned id
        //if our request body has a list of users then we map them as @Req.Body List<...> UserCreateDTO users
        userService.create(userCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //preauthorize if id exists
    @PutMapping("{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("Updating user with id {} with details {} ", id, userUpdateDTO);
        userService.update(id, userUpdateDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metadata can be passed with any http request.Headers are also
    // key value data-structures.This is the best way to map headers
    @GetMapping("map-headers-with-annotation")
    public ResponseEntity<Void> mapRequestHeadersUsingAnnotations(
            @RequestHeader("CLIENT-SECRET") String secret) {
        //by default required = true
        log.info("{}", secret);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //all passed headers will be mapped since we use map
    @GetMapping("map-headers-with-map")
    public ResponseEntity<Void> mapRequestHeadersUsingMap(
            @RequestHeader Map<String, String> headers) {
        log.info("{}", headers);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("respond-with-custom-http-headers")
    public ResponseEntity<Void> respondWithHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("FILE_NAME", "test.pdf");
        headers.set("FILE_EXTENSION", "pdf");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("pageable")//?page=0&size=10&sort=firstName,asc
    public ResponseEntity<Page<String>> paginationSearch(Pageable pageable) {
        //in this way we prevent user to request for example a million records from database
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        return new ResponseEntity<>(userService.printWithPagination(fixedPageable), HttpStatus.OK);
    }

    @GetMapping("slicing")//?page=0&size=10&sort=firstName,asc
    public ResponseEntity<Slice<String>> paginationSlice(Pageable pageable) {
        //in this way we prevent user to request for example a million records from database
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        return new ResponseEntity<>(userService.printWithSlice(fixedPageable), HttpStatus.OK);
    }

    //return custom field projections
    @GetMapping("projection/{department-id}")
    public ResponseEntity<List<UserFirstAndLastNameProjection>> find(
            @PathVariable("department-id") Integer departmentId) {
        return ResponseEntity.ok(userService.findProjection(departmentId));
    }
}