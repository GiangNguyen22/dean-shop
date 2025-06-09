package com.mr.deanshop.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map upLoadFile(MultipartFile file, String publicId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("folder", "products");

            if (publicId != null && !publicId.isEmpty()) {
                params.put("public_id", publicId);
                params.put("overwrite", true); // Ghi đè nếu đã tồn tại
            }

            return cloudinary.uploader().upload(file.getBytes(), params);
        } catch (IOException e) {
            throw new RuntimeException("Upload product image failed", e);
        }
    }
}