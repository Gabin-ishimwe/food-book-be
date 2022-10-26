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

import java.io.IOException;
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

    public ResponseEntity<?> updateMeal(MealDto mealDto, Long mealId, Long businessId) throws NotFoundException, IOException {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
//        Meal findMeal = mealRepository.findById(mealId).orElseThrow(() -> new NotFoundException("Meal not found"));
        Meal findMeal = null;
//        for (Meal meal : findBusiness.getMeals()) {
        for (int i = 0; i < findBusiness.getMeals().size(); i++) {
            do {
                findMeal = meal;
            } while (findMeal != null);
//            System.out.println(meal + "-------");
//            System.out.println(meal[i].getId() + "---ID");
            if (Objects.equals(findBusiness.getMeals().get(i).getId(), mealId)) {
//                System.out.println(meal);
//                Meal findMeal = mealRepository.findById(mealId).orElseThrow(() -> new NotFoundException("Meal not found"));
                findMeal = findBusiness.getMeals().get(i);
                i--;
                break;
            } else {
//                System.out.println(findMeal);
//                System.out.println(meal);
                throw new NotFoundException("Meal not found in the business");
            }
        }

        assert findMeal != null;
        if (!Objects.equals(findMeal.getName(), mealDto.getMenuName()) && mealDto.getMenuName() != null) {
            findMeal.setName(mealDto.getMenuName());
        }
        if (!Objects.equals(findMeal.getDescription(), mealDto.getMenuDescription()) && mealDto.getMenuDescription() != null) {
            findMeal.setDescription(mealDto.getMenuDescription());
        }
        if (!Objects.equals(findMeal.getPrice(), mealDto.getPrice()) && mealDto.getPrice() != null) {
            findMeal.setPrice(mealDto.getPrice());
        }
        if (mealDto.getImages() != null) {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile image : mealDto.getImages()) {
                if (!image.isEmpty() && Objects.requireNonNull(image.getContentType()).toLowerCase().startsWith("image")) {
                    imageUrls.add(cloudinaryUtil.uploadImage(image));
                } else {
                    throw new MultipartException("Upload valid Images");
                }
            }
            findMeal.setImages(imageUrls);
        }
        Meal updatedMeal = mealRepository.save(findMeal);

        return new ResponseEntity<>(new ResponseDto("Meal updated", updatedMeal), HttpStatus.OK);
    }

    public ResponseEntity<?> findOneBusinessMeal(Long businessId, Long mealId) throws NotFoundException {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
        for (Meal meal : findBusiness.getMeals()) {
            if (Objects.equals(meal.getId(), mealId)) {
//                Meal findMeal = mealRepository.findById(mealId).orElseThrow(() -> new NotFoundException("Meal not found"));
                return new ResponseEntity<>(meal, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There was an error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Meal not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> findOneMeal(Long mealId) throws NotFoundException {
        Meal findMeal = mealRepository.findById(mealId).orElseThrow(() -> new NotFoundException("Meal not found"));
        return new ResponseEntity<>(findMeal, HttpStatus.OK);
    }

    public ResponseEntity<?> findAllMeals() {
        List<Meal> allMeals = mealRepository.findAll();
        return new ResponseEntity<>(allMeals, HttpStatus.OK);
    }

    public ResponseEntity<?> findBusinessMeal(Long businessId) throws NotFoundException {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
        return new ResponseEntity<>(findBusiness.getMeals(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAllMeals() {
        mealRepository.deleteAll();
        return new ResponseEntity<>("All meals deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBusinessMeal(Long businessId, Long mealId) throws NotFoundException {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
        for (Meal meal : findBusiness.getMeals()) {
            if (Objects.equals(meal.getId(), mealId)) {
                mealRepository.deleteById(mealId);
                return new ResponseEntity<>("Meal deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There was an error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }

    public ResponseEntity<?> deleteAllBusinessMeals(Long businessId) throws NotFoundException {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
        for (Meal meal : findBusiness.getMeals()) {
            mealRepository.deleteById(meal.getId());
            return new ResponseEntity<>("All business meals deleted", HttpStatus.OK);
        }
        return null;
    }

}
