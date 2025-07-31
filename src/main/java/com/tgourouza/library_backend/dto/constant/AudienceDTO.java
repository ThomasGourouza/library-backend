package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.Audience;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AudienceDTO {
    private Long id;
    private Audience name;
}
