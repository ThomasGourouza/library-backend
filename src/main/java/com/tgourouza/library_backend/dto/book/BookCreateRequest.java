package com.tgourouza.library_backend.dto.book;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.openLibrary.Text;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {
    @NotBlank(message = "Original title is required")
    @Size(max = 255)
    private Text originalTitle;
    private String authorOLKey;
    private int publicationYear;
    private String coverUrl;
    private int numberOfPages;
    private Text description;
    private Set<String> tags;
    private Multilingual wikipediaLink;

    private UUID authorId;
    private String personalNotes; // can be empty
    private Status status; // can be empty & default value
    private Boolean favorite; // can be empty & default value

    public BookCreateRequest(
            Text originalTitle,
            String authorOLKey,
            int publicationYear,
            String coverUrl,
            int numberOfPages,
            Text description,
            Set<String> tags,
            Multilingual wikipediaLink
    ) {
        this.originalTitle = originalTitle;
        this.authorOLKey = authorOLKey;
        this.publicationYear = publicationYear;
        this.coverUrl = coverUrl;
        this.numberOfPages = numberOfPages;
        this.description = description;
        this.tags = tags != null ? tags : new HashSet<>();
        this.wikipediaLink = wikipediaLink;
        this.authorId = null;
        this.personalNotes = "";
        this.status = Status.UNREAD;
        this.favorite = false;
    }
}
