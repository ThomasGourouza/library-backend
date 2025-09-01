package com.tgourouza.library_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimePlace {
    private LocalDate date;
    private String city;
    private String country;
}
