package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.LiteraryMovement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiteraryMovementDTO {
    private Long id;
    private LiteraryMovement name;
}
