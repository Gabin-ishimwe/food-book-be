package com.backend.app.foodbook.business.controller;

import com.backend.app.foodbook.business.dto.BusinessDto;
import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.business.service.BussinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
}
