package com.reactive.wellnesswidgetservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WellnessDataRequest {
    @NotBlank(message = "userName can't be null or empty")
    String userName;
    String country;
    @NotNull(message = "latitude can't be null or empty")
    double latitude;
    @NotNull (message = "longitude can't be null or empty")
    double longitude;
}
