package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Country;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountryCreateRequest {
    @NotNull(message = "Country name must not be null")
    private Country name;
}
