package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.constant.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusUpdateRequest {
    @NotNull(message = "StatusId must not be null")
    private Long statusId;
}
