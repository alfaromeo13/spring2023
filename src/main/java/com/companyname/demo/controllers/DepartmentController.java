package com.companyname.demo.controllers;

import com.companyname.demo.dto.DepartmentDTO;
import com.companyname.demo.entities.Department;
import com.companyname.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("without")
    public ResponseEntity<Page<Department>> findWithout(Pageable pageable, @RequestParam String name) {
        return new ResponseEntity<>(departmentService.findByNameWithoutUsers(name, pageable), HttpStatus.OK);
    }

    @GetMapping("with")
    public ResponseEntity<Page<Department>> findWith(Pageable pageable, @RequestParam String name) {
        return new ResponseEntity<>(departmentService.findByNameWithUsers(name, pageable), HttpStatus.OK);
    }

    @GetMapping("pagination") //best way!
    public ResponseEntity<List<DepartmentDTO>> findWithBestWay(Pageable pageable, @RequestParam String name) {
        return new ResponseEntity<>(departmentService.findByNameWithUsersBestWay(name, pageable), HttpStatus.OK);
    }
}
