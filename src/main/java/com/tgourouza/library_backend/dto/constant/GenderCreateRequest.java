package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenderCreateRequest {
    @NotNull(message = "Gender name must not be null")
    private Gender name;
}
