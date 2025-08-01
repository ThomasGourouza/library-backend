package com.tgourouza.library_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multilingual {
    private String french;
    private String spanish;
    private String italian;
    private String portuguese;
    private String english;
    private String german;
    private String russian;
    private String japanese;
}
