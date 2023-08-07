package com.reactive.wellnesswidgetservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddUserRequest {

    @NotBlank(message = "userName can't be empty or null")
    String userName;
    @NotBlank(message = "city can't be empty or null")
    String city;
    @NotBlank(message = "country can't be empty or null")
    String country;

}
