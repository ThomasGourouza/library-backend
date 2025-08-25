package com.tgourouza.library_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfoTranslated {
    private String oLKey;
    private String name;
    private String pictureUrl;

    private Multilingual description;

    private Multilingual shortDescription;

    private TimePlaceTranslated birth;

    private TimePlaceTranslated death;

    private MultilingualList citizenships;
    private MultilingualList occupations;
    private MultilingualList languages;

    private Multilingual wikipediaLinks;
}
