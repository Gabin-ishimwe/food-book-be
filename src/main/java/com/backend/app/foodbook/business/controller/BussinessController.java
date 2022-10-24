package com.backend.app.foodbook.business.controller;

import com.backend.app.foodbook.business.dto.BusinessDto;
import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.business.service.BussinessService;
import com.backend.app.foodbook.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(path = "/api/business")
@Api(tags = "Business")
public class BussinessController {

    private final BussinessService businessService;

    @Autowired
    BussinessController(BussinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    @ApiOperation(
            value = "Create Business",
            notes = "Vendors creating their business",
            response = Business.class
    )
    public Business createBusiness(@RequestBody @Valid BusinessDto businessDto, HttpServletRequest request) throws Exception {
        String extractToken = request.getHeader("Authorization").split(" ")[1];
        return businessService.createBusiness(businessDto, extractToken);
    }

    @PutMapping(path = "{businessId}")
    @ApiOperation(
            value = "Update Business",
            notes = "Api to update business information",
            response = Business.class
    )
    @PreAuthorize("hasRole('VENDOR')")
    public Business editBusiness(@RequestBody @Valid BusinessDto businessDto, @PathVariable("businessId") @NotBlank(message = "Business id is required") Long businessId) throws NotFoundException {
        return businessService.editBusiness(businessDto, businessId);
    }

    @GetMapping(path = "{businessId}")
    @ApiOperation(
            value = "Get one business",
            notes = "Api to get one business",
            response = Business.class
    )
    @PreAuthorize("hasRole('VENDOR')")
    public Business getOneBusiness(@PathVariable("businessId") @NotBlank(message = "Business id is required") Long businessId) throws NotFoundException {
        return businessService.getOneBusiness(businessId);
    }

    @GetMapping
    @ApiOperation(
            value = "Get all business",
            notes = "Api to get all businesses",
            response = Business.class
    )
    @PreAuthorize("hasRole('VENDOR')")
    public List<Business> getAllBusiness() {
        return businessService.getAllBusiness();
    }


}
