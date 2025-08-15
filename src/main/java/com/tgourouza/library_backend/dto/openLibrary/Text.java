package com.tgourouza.library_backend.dto.openLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Text {
    private String value;
    private String language;
}
