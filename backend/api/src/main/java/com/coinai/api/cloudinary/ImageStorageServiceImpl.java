package com.coinai.api.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of()
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error uploading image",
                    e
            );

        }

    }

}