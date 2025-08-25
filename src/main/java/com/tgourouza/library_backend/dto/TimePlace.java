package com.tgourouza.library_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TimePlace {
    private LocalDate date;
    private String city;
    private String country;
}
