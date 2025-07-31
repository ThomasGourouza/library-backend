package com.tgourouza.library_backend.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthorDate {
    private LocalDate birth;
    private LocalDate death;
}
