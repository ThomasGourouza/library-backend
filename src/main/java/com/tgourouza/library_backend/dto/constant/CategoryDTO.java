package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private Category name;
}
