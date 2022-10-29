package com.backend.app.foodbook.order.service;

import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.meal.entity.Meal;
import com.backend.app.foodbook.meal.repository.MealRepository;
import com.backend.app.foodbook.order.dto.OrderDto;
import com.backend.app.foodbook.order.dto.ResponseDto;
import com.backend.app.foodbook.order.entity.Order;
import com.backend.app.foodbook.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final MealRepository mealRepository;

    @Autowired
    OrderService(OrderRepository orderRepository, MealRepository mealRepository) {
        this.orderRepository = orderRepository;
        this.mealRepository = mealRepository;
    }

    public ResponseEntity<?> createOrder(OrderDto orderDto) throws NotFoundException {
        List<Meal> meals = null;
        Long price = 0L;
        for (Long id : orderDto.getMealId()) {
            Meal findMeal = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal doesn't exist"));
            meals.add(findMeal);
            price += findMeal.getPrice();
        }
        Order order = Order.builder()
                .meal(meals)
                .amount(price)
                .build();

        Order savedOrder = orderRepository.save(order);
        return new ResponseEntity<>(new ResponseDto("Order Placed", savedOrder), HttpStatus.CREATED);
    }
}
