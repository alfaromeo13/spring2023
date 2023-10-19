package com.companyname.demo.controllers;

import com.companyname.demo.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("upload")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile multipartFile) {
        documentService.upload(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Integer id) {
        return ResponseEntity.ok(new ByteArrayResource(documentService.download(id)));
    }
}
