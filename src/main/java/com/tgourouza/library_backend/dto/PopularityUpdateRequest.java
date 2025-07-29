package com.tgourouza.library_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularityUpdateRequest {
    @Min(0)
    @Max(100)
    private Integer popularityEurope;

    // Getters and setters
}
