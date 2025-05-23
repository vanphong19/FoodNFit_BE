package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vanphong.foodnfitbe.application.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private Cloudinary cloudinary;
    @Value("${cloudinary.upload_preset}")
    private String uploadPreset;


    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Map params = ObjectUtils.asMap(
           "upload_preset", uploadPreset );

        Map result = cloudinary.uploader().upload(file.getBytes(), params);
        return result.get("secure_url").toString();
    }
}
