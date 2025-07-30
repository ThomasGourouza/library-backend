package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusCreateRequest {
    @NotNull(message = "Status name must not be null")
    private Status name;
}
