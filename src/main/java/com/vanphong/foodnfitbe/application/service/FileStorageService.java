package com.vanphong.foodnfitbe.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
public interface FileStorageService {
    public String uploadImage(MultipartFile file) throws Exception;
}
