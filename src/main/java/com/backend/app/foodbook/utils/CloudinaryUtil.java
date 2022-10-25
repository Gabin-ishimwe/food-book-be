package com.backend.app.foodbook.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryUtil {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile image) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }

    public String imageName(String name) {
        String newName;
        if (name.split(" ").length > 1) {
            newName = String.join("_", name.split(" "));
            System.out.println(newName);
            return newName;
        }
        newName = String.join("", name.split(" "));
        return newName;

    }
}
