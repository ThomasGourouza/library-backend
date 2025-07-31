package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Type;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TypeCreateRequest {
    @NotNull(message = "Literary genre name must not be null")
    private Type name;
}
