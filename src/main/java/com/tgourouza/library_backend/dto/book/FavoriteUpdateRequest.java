package com.tgourouza.library_backend.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteUpdateRequest {
    @NotNull(message = "Favorite must not be null")
    private Boolean favorite;
}
