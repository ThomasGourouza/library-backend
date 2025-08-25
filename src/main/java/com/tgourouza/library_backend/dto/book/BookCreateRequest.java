package com.tgourouza.library_backend.dto.book;

import java.util.UUID;

import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.openLibrary.Text;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookCreateRequest {
    @NotBlank(message = "Original title is required")
    @Size(max = 255)
    private String originalTitle;
    private Language language; // title language
    @NotNull
    private UUID authorId;

    private String authorOLKey;
    private int publicationYear;
    private String coverUrl;
    private int numberOfPages;
    private Text description;
    private Text tags;
    private Multilingual wikipediaLink;

    private String personalNotes; // can be empty
    private String status; // can be empty & default value
    private Boolean favorite; // can be empty & default value
}
