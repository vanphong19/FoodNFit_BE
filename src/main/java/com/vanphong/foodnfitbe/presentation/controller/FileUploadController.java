package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FileStorageService;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = fileStorageService.uploadImage(file);
            return ResponseEntity.ok(new UploadResponse(url));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new UploadResponse(null));
        }
    }
}
