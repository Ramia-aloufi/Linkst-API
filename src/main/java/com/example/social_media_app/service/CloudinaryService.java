package com.example.social_media_app.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public Map<String, String> uploadFile(MultipartFile file,String folderName) throws IOException {
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "resource_type", "auto",
            "public_id", folderName + "/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename(),
            "chunk_size", 3000000
        ));

        String url = uploadResult.get("secure_url").toString();
        String resourceType = uploadResult.get("resource_type").toString(); // "image", "video"

        return Map.of(
                "url", url,
                "type", resourceType);
    }

}
