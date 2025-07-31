package com.tgourouza.library_backend.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthorIdentity {
    private LocalDate birthDate;
    private LocalDate deathDate;
}
