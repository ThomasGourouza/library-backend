package com.tgourouza.library_backend.dto.book;

import com.tgourouza.library_backend.constant.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    @NotNull(message = "Status must not be null")
    private Status status;
}
