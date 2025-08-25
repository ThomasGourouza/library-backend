package com.tgourouza.library_backend.dto.openLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorOpenLibrary {
    private String oLKey;
    private String wikidataId;
    private String name;
    private String pictureUrl;
    private Text description;
}
