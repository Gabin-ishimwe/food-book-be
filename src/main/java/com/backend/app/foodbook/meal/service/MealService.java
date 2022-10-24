package com.backend.app.foodbook.meal.service;

import com.backend.app.foodbook.meal.dto.MealDto;
import com.backend.app.foodbook.meal.dto.ResponseDto;
import com.backend.app.foodbook.meal.entity.Meal;
import com.backend.app.foodbook.meal.repository.MealRepository;
import com.backend.app.foodbook.utils.CloudinaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MealService {

    private final MealRepository mealRepository;

    private final CloudinaryUtil cloudinaryUtil;

    @Autowired
    MealService(MealRepository mealRepository, CloudinaryUtil cloudinaryUtil) {
        this.mealRepository = mealRepository;
        this.cloudinaryUtil = cloudinaryUtil;
    }

    public ResponseEntity<?> createMealService(MealDto mealDto) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        if (mealDto.getImages() == null) {
            throw new MultipartException("Images are required");
        }
        for (MultipartFile image : mealDto.getImages()) {
            if (Objects.requireNonNull(image.getContentType()).toLowerCase().startsWith("image")) {
                System.out.println("inside if statement");
                imageUrls.add(cloudinaryUtil.uploadImage(image));
            }
            System.out.println(Objects.requireNonNull(image.getContentType()).toLowerCase().startsWith("image"));
            throw new MultipartException("Upload valid image");
        }
        Meal meal = new Meal(
                null,
                mealDto.getMenuName(),
                mealDto.getMenuDescription(),
                imageUrls,
                mealDto.getPrice()
        );

        Meal createdMeal = mealRepository.save(meal);
        return new ResponseEntity<>(new ResponseDto("Meal created", createdMeal), HttpStatus.CREATED);
    }
}
