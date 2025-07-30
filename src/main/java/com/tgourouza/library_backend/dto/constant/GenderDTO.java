package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenderDTO {
    private Long id;
    private Gender name;
}
