package com.backend.app.foodbook.business.dto;

import javax.validation.constraints.NotBlank;

public class BusinessDto {

    @NotBlank(
            message = "Business name is required"
    )
    private String businessName;

    @NotBlank(
            message = "Business description is required"
    )
    private String businessDescription;

    @NotBlank(
            message = "Business contact is required"
    )
    private String businessContact;

    @NotBlank(
            message = "Business email is required"
    )
    private String businessEmail;
}
