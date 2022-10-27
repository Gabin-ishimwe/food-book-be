package com.backend.app.foodbook.Menu.controller;

import com.backend.app.foodbook.Menu.dto.MenuDto;
import com.backend.app.foodbook.Menu.service.MenuService;
import com.backend.app.foodbook.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/menu")
@Api(tags = "Menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Create Menu",
            notes = "Add meal to menu"
    )
    public ResponseEntity<?> createMenu(@RequestBody @Valid MenuDto menuDto) throws Exception {
        return menuService.createMenu(menuDto);
    }

    @PutMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "updated Menu",
            notes = "update stock number of meal on menu"
    )
    public ResponseEntity<?> updateMenu(@RequestParam("menuId") Long menuId, @RequestParam("stockNumber") Long stockNumber) throws Exception {
        return menuService.updateMenu(menuId, stockNumber);
    }

    @GetMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Get Menu",
            notes = "Get all daily meals on menu"
    )
    public ResponseEntity<?> allMenu() {
        return menuService.getAllMealMenu();
    }

    @GetMapping(path = "{menuId}")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Get one Menu",
            notes = "Get one daily meal on menu"
    )
    public ResponseEntity<?> oneMenu(@PathVariable("menuId") Long menuId) throws NotFoundException {
        return menuService.getOneMenu(menuId);
    }

    @DeleteMapping(path = "{menuId}")
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Delete one Menu",
            notes = "Delete one daily meal on menu"
    )
    public ResponseEntity<?> deleteOneMenu(@PathVariable("menuId") Long menuId) throws NotFoundException {
        return menuService.deleteOneMealMenu(menuId);
    }
}
