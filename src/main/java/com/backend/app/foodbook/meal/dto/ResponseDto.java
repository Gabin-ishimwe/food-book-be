package com.backend.app.foodbook.meal.dto;

import com.backend.app.foodbook.meal.entity.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String message;
    private Meal meal;
}
