package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryCreateRequest {
    @NotNull(message = "Category name must not be null")
    private Category name;
}
