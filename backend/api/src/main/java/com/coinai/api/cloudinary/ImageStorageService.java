package com.coinai.api.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String upload(MultipartFile file);

}