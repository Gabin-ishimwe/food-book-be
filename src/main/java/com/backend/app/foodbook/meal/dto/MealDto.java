package com.backend.app.foodbook.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
    @NotBlank(
            message = "Meal name is required"
    )
    private String menuName;

    @NotBlank(
            message = "Meal description is required"
    )
    private String menuDescription;

    //    @NotBlank(
//            message = "Meal image is required"
//    )
    private MultipartFile[] images;

    //    @NotEmpty(
//            message = "Meal price is required"
//    )
//    @NotBlank(message = "Meal price is required")
    @NotNull(
            message = "Meal price is required"
    )
    private int price;
}
