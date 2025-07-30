package com.tgourouza.library_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularityUpdateRequest {
    @NotNull(message = "Popularity must not be null")
    @Min(value = 0, message = "Popularity must be at least 0")
    @Max(value = 100, message = "Popularity must be at most 100")
    private Integer popularityEurope;

    // Getters and setters
}
