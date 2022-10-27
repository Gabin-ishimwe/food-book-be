package com.backend.app.foodbook.Menu.service;

import com.backend.app.foodbook.Menu.dto.MenuDto;
import com.backend.app.foodbook.Menu.dto.ResponseDto;
import com.backend.app.foodbook.Menu.entity.Menu;
import com.backend.app.foodbook.Menu.repository.MenuRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.meal.entity.Meal;
import com.backend.app.foodbook.meal.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final MealRepository mealRepository;

    @Autowired
    MenuService(MenuRepository menuRepository, MealRepository mealRepository) {
        this.menuRepository = menuRepository;
        this.mealRepository = mealRepository;
    }

    public ResponseEntity<?> createMenu(MenuDto menuDto) throws Exception {
        Meal findMeal = mealRepository.findById(menuDto.getMealId()).orElseThrow(() -> new NotFoundException("Meal doesn't exist"));
        if (findMeal.getOnMenu()) {
            throw new Exception("Meal already on Menu");
        }
        Menu dailyMenu = new Menu(
                null,
                menuDto.getStockNumber(),
                findMeal
        );

        Menu menu = menuRepository.save(dailyMenu);
        findMeal.setOnMenu(true);
        mealRepository.save(findMeal);

        return new ResponseEntity<>(new ResponseDto("Meal added to menu", menu), HttpStatus.CREATED);

    }

    public ResponseEntity<?> updateMenu(Long menuId, Long stockNumber) throws NotFoundException {
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu not found"));
        findMenu.setStockNumber(stockNumber);
        Menu updatedMenu = menuRepository.save(findMenu);
        return new ResponseEntity<>(new ResponseDto("Menu updated", updatedMenu), HttpStatus.OK);
    }

    public ResponseEntity<?> getOneMenu(Long menuId) throws NotFoundException {
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu not found"));
        return new ResponseEntity<>(findMenu, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllMealMenu() {
        return new ResponseEntity<>(menuRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteOneMealMenu(Long menuId) throws NotFoundException {
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu not found"));
        menuRepository.delete(findMenu);
        return new ResponseEntity<>("Menu deleted", HttpStatus.OK);

    }
}
