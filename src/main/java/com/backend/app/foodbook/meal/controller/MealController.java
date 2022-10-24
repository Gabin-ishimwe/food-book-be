package com.backend.app.foodbook.meal.controller;

import com.backend.app.foodbook.meal.dto.MealDto;
import com.backend.app.foodbook.meal.service.MealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/meal")
@Api(tags = "Meal")
public class MealController {

    private final MealService mealService;

    @Autowired
    MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Create Meal Menu",
            notes = "Api to create a meal on business menu"
    )
    public ResponseEntity<?> createMeal(@ModelAttribute @Valid MealDto mealDto) throws IOException {
        return mealService.createMealService(mealDto);
    }
}
