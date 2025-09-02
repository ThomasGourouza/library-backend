package com.tgourouza.library_backend.dto;

import java.time.LocalDate;

public record TimePlace(
        LocalDate date,
        String city,
        String country) {
}
