package com.tgourouza.library_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimePlaceTranslated {
    private LocalDate date;
    private String city;
    private Multilingual country;
}
