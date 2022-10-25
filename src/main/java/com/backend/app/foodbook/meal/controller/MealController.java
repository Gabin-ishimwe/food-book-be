package com.backend.app.foodbook.meal.controller;

import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.meal.dto.MealDto;
import com.backend.app.foodbook.meal.service.MealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> createMeal(@ModelAttribute @Valid MealDto mealDto, @RequestParam("businessId") Long businessId, HttpServletRequest request) throws IOException, NotFoundException {
        String token = request.getHeader("Authorization");
        return mealService.createMealService(mealDto, businessId, token);
    }
}
