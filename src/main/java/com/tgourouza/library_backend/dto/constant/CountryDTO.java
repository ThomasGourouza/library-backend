package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Country;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryDTO {
    private Long id;
    private Country name;
}
