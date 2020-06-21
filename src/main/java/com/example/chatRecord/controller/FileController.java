package com.example.chatRecord.controller;

import com.example.chatRecord.service.FileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/{id}")
    public String upload(@NotNull MultipartFile src, @PathVariable @NotNull String id) throws IOException {
        fileService.upload(src, id);
        return "success";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable @NotNull String id) {
        var byteArray = fileService.download(id);
        var status = byteArray == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED;
        return new ResponseEntity<>(byteArray, status);
    }
}
