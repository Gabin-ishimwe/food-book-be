package com.backend.app.foodbook.meal.controller;

import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.meal.dto.MealDto;
import com.backend.app.foodbook.meal.service.MealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/meal")
@Api(tags = "Meal")
public class MealController {

    private final MealService mealService;

    @Autowired
    MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Create Meal Menu",
            notes = "Api to create a meal on business menu"
    )
    public ResponseEntity<?> createMeal(@ModelAttribute @Valid MealDto mealDto, @RequestParam("businessId") Long businessId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").split(" ")[1];
        return mealService.createMealService(mealDto, businessId, token);
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Update Meal Menu",
            notes = "Api to update a meal on business menu"
    )
    public ResponseEntity<?> updateMeal(@ModelAttribute MealDto mealDto, @RequestParam("businessId") Long businessId, @RequestParam("mealId") Long mealId) throws Exception {
        return mealService.updateMeal(mealDto, mealId, businessId);
    }

    @GetMapping(path = "/business/one-meal")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Get One Meal from business",
            notes = "Api to get one meal from a specific business"
    )
    public ResponseEntity<?> findOneBusinessMeal(@RequestParam("businessId") Long businessId, @RequestParam("mealId") Long mealId) throws NotFoundException {
        return mealService.findOneBusinessMeal(businessId, mealId);
    }

    @GetMapping(path = "/one-meal/{mealId}")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Get One Meal",
            notes = "Api to get one meal"
    )
    public ResponseEntity<?> findOneMeal(@PathVariable("mealId") Long mealId) throws NotFoundException {
        return mealService.findOneMeal(mealId);
    }


    @GetMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Get All Meals",
            notes = "Api to get all meals in the database"
    )
    public ResponseEntity<?> findAllMeal() {
        return mealService.findAllMeals();
    }

    @GetMapping(path = "/business/{businessId}")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Get All Meals in Particular business",
            notes = "Api to get all meals in a business"
    )
    public ResponseEntity<?> findBusinessMeal(@PathVariable("businessId") Long id) throws NotFoundException {
        return mealService.findBusinessMeal(id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Delete all meals in the database",
            notes = "Api to delete all meals in a business"
    )
    public ResponseEntity<?> deleteAllMeals() throws NotFoundException {
        return mealService.deleteAllMeals();
    }

    @DeleteMapping(path = "/business/one-meal")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Delete one meal in business",
            notes = "Api to delete one meal in particular business"
    )
    public ResponseEntity<?> deleteOneBusinessMeal(@RequestParam("businessId") Long businessId, @RequestParam("mealId") Long mealId) throws Exception {
        return mealService.deleteBusinessMeal(businessId, mealId);
    }

    @DeleteMapping(path = "/business")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Delete all meals in business",
            notes = "Api to delete all meals in particular business"
    )
    public ResponseEntity<?> deleteAllBusinessMeals(@RequestParam("businessId") Long businessId) throws NotFoundException {
        return mealService.deleteAllBusinessMeals(businessId);
    }
}
