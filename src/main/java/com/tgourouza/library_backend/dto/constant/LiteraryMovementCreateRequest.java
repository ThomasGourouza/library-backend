package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.LiteraryMovement;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiteraryMovementCreateRequest {
    @NotNull(message = "Literary movement name must not be null")
    private LiteraryMovement name;
}
