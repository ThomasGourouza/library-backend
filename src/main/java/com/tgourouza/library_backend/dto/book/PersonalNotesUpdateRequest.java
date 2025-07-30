package com.tgourouza.library_backend.dto.book;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonalNotesUpdateRequest {
    @Size(max = 1000, message = "Personal notes must not exceed 1000 characters")
    private String personalNotes;
}
