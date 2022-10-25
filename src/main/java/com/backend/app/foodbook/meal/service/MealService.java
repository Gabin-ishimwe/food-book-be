package com.backend.app.foodbook.meal.service;

import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.business.repository.BusinessRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.meal.dto.MealDto;
import com.backend.app.foodbook.meal.dto.ResponseDto;
import com.backend.app.foodbook.meal.entity.Meal;
import com.backend.app.foodbook.meal.repository.MealRepository;
import com.backend.app.foodbook.utils.CloudinaryUtil;
import com.backend.app.foodbook.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MealService {

    private final MealRepository mealRepository;

    private final CloudinaryUtil cloudinaryUtil;

    private final BusinessRepository businessRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    MealService(MealRepository mealRepository, CloudinaryUtil cloudinaryUtil, BusinessRepository businessRepository, JwtUtil jwtUtil) {
        this.mealRepository = mealRepository;
        this.cloudinaryUtil = cloudinaryUtil;
        this.businessRepository = businessRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> createMealService(MealDto mealDto, Long businessId, String token) throws Exception {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
        String userEmail = jwtUtil.getUserNameFromToken(token);
        if (!Objects.equals(findBusiness.getUser().getEmail(), userEmail)) {
            throw new Exception("This business doesn't belong to you !!!");
        }
        List<Meal> businessMeals = findBusiness.getMeals();
        for (Meal businessMeal : businessMeals) {
            if (Objects.equals(businessMeal.getName(), mealDto.getMenuName())) {
                return new ResponseEntity<>(new ResponseDto("Meals can't have the same name", null), HttpStatus.OK);
            }
        }

        List<String> imageUrls = new ArrayList<>();
        if (mealDto.getImages() == null) {
            throw new MultipartException("Images are required");
        }
        System.out.println(mealDto.getImages().length);
        for (MultipartFile image : mealDto.getImages()) {
            if (!image.isEmpty() && Objects.requireNonNull(image.getContentType()).toLowerCase().startsWith("image")) {
                imageUrls.add(cloudinaryUtil.uploadImage(image));
            } else {
                throw new MultipartException("Upload valid Images");
            }
        }
        Meal meal = new Meal(
                null,
                mealDto.getMenuName(),
                mealDto.getMenuDescription(),
                imageUrls,
                mealDto.getPrice()
        );


        Meal createdMeal = mealRepository.save(meal);
        businessMeals.add(createdMeal);
        findBusiness.setMeals(businessMeals);
        businessRepository.save(findBusiness);

        return new ResponseEntity<>(new ResponseDto("Meal created", createdMeal), HttpStatus.CREATED);
    }
}
