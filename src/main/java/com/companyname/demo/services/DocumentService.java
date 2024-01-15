package com.companyname.demo.services;

import com.companyname.demo.entities.Document;
import com.companyname.demo.errors.DocumentNotFoundException;
import com.companyname.demo.repositories.DocumentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    //@Value("${documents.base-path}")
    private String documentsBasePath;

    private final DocumentRepository documentRepository;

    @SneakyThrows
    @Transactional(rollbackFor = IOException.class)
    public void upload(MultipartFile multipartFile) {
        //multipartFile.getSize(); //size in bytes
        String fileName = multipartFile.getOriginalFilename(); //test.pdf
        String fullPath = documentsBasePath +
                RandomStringUtils.randomAlphabetic(10) + fileName;
        Document document = new Document();
        document.setName(fileName);
        document.setPath(fullPath);
        documentRepository.save(document);
        //now we save file on filesystem with java.nio
        Path path = Paths.get(fullPath); //checks whether path exists on filesystem
        Files.write(path, multipartFile.getBytes());
    }

    @SneakyThrows
    public byte[] download(Integer id, HttpServletResponse response) {
        //TODO: return custom DTO class with 2 attributes (byte[] file + String fileName)
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            Path path = Paths.get(document.getPath());
            //with this we return file name
            response.setHeader("FILE-NAME", document.getName());
            return Files.readAllBytes(path);
        } else throw new DocumentNotFoundException("Document with id " + id + " not found!");
        //since this is our custom class, we need to handle it globally to avoid 500 error
    }
}