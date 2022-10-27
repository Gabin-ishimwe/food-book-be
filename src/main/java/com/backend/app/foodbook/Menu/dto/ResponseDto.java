package com.backend.app.foodbook.Menu.dto;

import com.backend.app.foodbook.Menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String message;
    private Menu menu;
}
