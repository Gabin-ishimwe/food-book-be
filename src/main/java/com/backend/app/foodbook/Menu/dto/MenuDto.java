package com.backend.app.foodbook.Menu.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MenuDto {
    @NonNull()
    private Long mealId;
    @NonNull()
    private Long stockNumber;
}
