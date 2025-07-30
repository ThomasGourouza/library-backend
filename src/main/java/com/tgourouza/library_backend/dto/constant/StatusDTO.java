package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusDTO {
    private Long id;
    private Status name;
}
