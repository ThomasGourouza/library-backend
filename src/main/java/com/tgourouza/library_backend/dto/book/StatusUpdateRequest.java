package com.tgourouza.library_backend.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    @NotNull(message = "StatusId must not be null")
    private Long statusId;
}
