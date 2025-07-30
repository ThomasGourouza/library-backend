package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Language;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LanguageCreateRequest {
    @NotNull(message = "Language name must not be null")
    private Language name;
}
