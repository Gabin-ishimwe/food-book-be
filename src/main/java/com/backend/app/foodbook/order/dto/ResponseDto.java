package com.backend.app.foodbook.order.dto;

import com.backend.app.foodbook.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private String message;
    private Order order;
}
