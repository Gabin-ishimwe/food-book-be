package com.backend.app.foodbook.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
