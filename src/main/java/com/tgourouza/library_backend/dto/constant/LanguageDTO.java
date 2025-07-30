package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Language;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageDTO {
    private Long id;
    private Language name;
}
