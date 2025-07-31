package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Audience;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AudienceCreateRequest {
    @NotNull(message = "Category name must not be null")
    private Audience name;
}
