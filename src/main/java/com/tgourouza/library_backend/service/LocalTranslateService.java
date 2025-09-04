package com.tgourouza.library_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.mapper.IsoLangMapper;

@Service
public class LocalTranslateService {

    private final MultilingualService multilingualService;
    private final IsoLangMapper isoLangMapper;

    public LocalTranslateService(
            MultilingualService multilingualService,
            IsoLangMapper isoLangMapper) {
        this.multilingualService = multilingualService;
        this.isoLangMapper = isoLangMapper;
    }

    public List<String> translateListFromEnglish(Type type, List<String> list, DataLanguage targetLanguage) {
        return list.stream()
                .map(item -> translateFromEnglish(type, item, targetLanguage))
                .toList();
    }

    public String translateFromEnglish(Type type, String item, DataLanguage targetLanguage) {
        return multilingualService.translateFromEnglish(type, item, isoLangMapper.toIso(targetLanguage))
                .orElse(item);
    }

    public String translateBookTitle(BookEntity book, DataLanguage dataLanguage) {
        return switch (dataLanguage) {
            case FRENCH -> book.getTitleFrench();
            case SPANISH -> book.getTitleSpanish();
            case ITALIAN -> book.getTitleItalian();
            case PORTUGUESE -> book.getTitlePortuguese();
            case ENGLISH -> book.getTitleEnglish();
            case GERMAN -> book.getTitleGerman();
            case RUSSIAN -> book.getTitleRussian();
            case JAPANESE -> book.getTitleJapanese();
            default -> book.getTitleEnglish();
        };
    }

    public String translateBookDescription(BookEntity book, DataLanguage dataLanguage) {
        return switch (dataLanguage) {
            case FRENCH -> book.getDescriptionFrench();
            case SPANISH -> book.getDescriptionSpanish();
            case ITALIAN -> book.getDescriptionItalian();
            case PORTUGUESE -> book.getDescriptionPortuguese();
            case ENGLISH -> book.getDescriptionEnglish();
            case GERMAN -> book.getDescriptionGerman();
            case RUSSIAN -> book.getDescriptionRussian();
            case JAPANESE -> book.getDescriptionJapanese();
            default -> book.getDescriptionEnglish();
        };
    }

    public String translateAuthorDescription(AuthorEntity author, DataLanguage dataLanguage) {
        return switch (dataLanguage) {
            case FRENCH -> author.getDescriptionFrench();
            case SPANISH -> author.getDescriptionSpanish();
            case ITALIAN -> author.getDescriptionItalian();
            case PORTUGUESE -> author.getDescriptionPortuguese();
            case ENGLISH -> author.getDescriptionEnglish();
            case GERMAN -> author.getDescriptionGerman();
            case RUSSIAN -> author.getDescriptionRussian();
            case JAPANESE -> author.getDescriptionJapanese();
            default -> author.getDescriptionEnglish();
        };
    }

    public String translateAuthorShortDescription(AuthorEntity author, DataLanguage dataLanguage) {
        return switch (dataLanguage) {
            case FRENCH -> author.getShortDescriptionFrench();
            case SPANISH -> author.getShortDescriptionSpanish();
            case ITALIAN -> author.getShortDescriptionItalian();
            case PORTUGUESE -> author.getShortDescriptionPortuguese();
            case ENGLISH -> author.getShortDescriptionEnglish();
            case GERMAN -> author.getShortDescriptionGerman();
            case RUSSIAN -> author.getShortDescriptionRussian();
            case JAPANESE -> author.getShortDescriptionJapanese();
            default -> author.getShortDescriptionEnglish();
        };
    }

    public String translateAuthorWikipediaLink(AuthorEntity author, DataLanguage dataLanguage) {
        return switch (dataLanguage) {
            case FRENCH -> author.getWikipediaLinkFrench();
            case SPANISH -> author.getWikipediaLinkSpanish();
            case ITALIAN -> author.getWikipediaLinkItalian();
            case PORTUGUESE -> author.getWikipediaLinkPortuguese();
            case ENGLISH -> author.getWikipediaLinkEnglish();
            case GERMAN -> author.getWikipediaLinkGerman();
            case RUSSIAN -> author.getWikipediaLinkRussian();
            case JAPANESE -> author.getWikipediaLinkJapanese();
            default -> author.getWikipediaLinkEnglish();
        };
    }
}
