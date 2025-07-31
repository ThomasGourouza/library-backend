package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthorIdentityDTO {
    private String firstName;
    private String name;
    private Gender gender;
    private Country country;
    private LocalDate birthDate;
    private LocalDate deathDate;
}
