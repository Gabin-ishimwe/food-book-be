package com.backend.app.foodbook.order.controller;

import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.order.dto.OrderDto;
import com.backend.app.foodbook.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Api(tags = "Order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(
            value = "Create an order",
            notes = "User creating an order"
    )
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) throws NotFoundException {
        System.out.println(orderDto.getMealId());
        return orderService.createOrder(orderDto);
    }
}
