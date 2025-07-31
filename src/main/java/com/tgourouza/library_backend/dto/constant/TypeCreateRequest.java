package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.LiteraryGenre;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiteraryGenreCreateRequest {
    @NotNull(message = "Literary genre name must not be null")
    private LiteraryGenre name;
}
