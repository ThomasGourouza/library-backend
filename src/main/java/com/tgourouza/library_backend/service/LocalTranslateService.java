package com.tgourouza.library_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import com.tgourouza.library_backend.mapper.IsoLangMapper;

@Service
public class LocalTranslateService {

    private final MultilingualService multilingualService;
    private final IsoLangMapper isoLangMapper;
    private final LibreTranslateService libreTranslateService;

    public LocalTranslateService(
            MultilingualService multilingualService,
            IsoLangMapper isoLangMapper,
            LibreTranslateService libreTranslateService) {
        this.multilingualService = multilingualService;
        this.isoLangMapper = isoLangMapper;
        this.libreTranslateService = libreTranslateService;
    }

    public List<String> translateListFromEnglish(Type type, List<String> list, DataLanguage targetLanguage) {
        return list.stream()
                .map(item -> translateFromEnglish(type, item, targetLanguage))
                .toList();
    }

    public String translateFromEnglish(Type type, String item, DataLanguage targetLanguage) {
        return multilingualService.translateFromEnglish(type, item, isoLangMapper.toIso(targetLanguage))
                .orElse(createTranslationFromEnglish(type, item, targetLanguage));
    }

    public String createTranslationFromEnglish(Type type, String item, DataLanguage targetLanguage) {
        if (!List.of(Type.OCCUPATION, Type.LANGUAGE, Type.COUNTRY).contains(type)) {
            return item;
        }
        return libreTranslateService.translateTextFromEnglish(item, targetLanguage);
    }

    // TODO: move somewhere else ?
    public String wikipediaLink(AuthorWikidata authorWikidata, DataLanguage dataLanguage) {
        return switch (dataLanguage) {
            case FRENCH -> authorWikidata.wikipediaFr();
            case SPANISH -> authorWikidata.wikipediaEs();
            case ITALIAN -> authorWikidata.wikipediaIt();
            case PORTUGUESE -> authorWikidata.wikipediaPt();
            case ENGLISH -> authorWikidata.wikipediaEn();
            case GERMAN -> authorWikidata.wikipediaDe();
            case RUSSIAN -> authorWikidata.wikipediaRu();
            case JAPANESE -> authorWikidata.wikipediaJa();
            default -> authorWikidata.wikipediaEn();
        };
    }
}
