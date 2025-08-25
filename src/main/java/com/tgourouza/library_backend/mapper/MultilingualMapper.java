package com.tgourouza.library_backend.mapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.MultilingualList;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;

@Component
public class MultilingualMapper {

    public Multilingual toMultilingualDescription(AuthorEntity author) {
        if (author == null)
            return null;
        return new Multilingual(
                author.getDescriptionFrench(),
                author.getDescriptionSpanish(),
                author.getDescriptionItalian(),
                author.getDescriptionPortuguese(),
                author.getDescriptionEnglish(),
                author.getDescriptionGerman(),
                author.getDescriptionRussian(),
                author.getDescriptionJapanese());
    }

    public Multilingual toMultilingualShortDescription(AuthorEntity author) {
        if (author == null)
            return null;
        return new Multilingual(
                author.getShortDescriptionFrench(),
                author.getShortDescriptionSpanish(),
                author.getShortDescriptionItalian(),
                author.getShortDescriptionPortuguese(),
                author.getShortDescriptionEnglish(),
                author.getShortDescriptionGerman(),
                author.getShortDescriptionRussian(),
                author.getShortDescriptionJapanese());
    }

    public Multilingual toMultilingualBirthCountry(AuthorEntity author) {
        if (author == null)
            return null;
        return new Multilingual(
                author.getBirthCountryFrench(),
                author.getBirthCountrySpanish(),
                author.getBirthCountryItalian(),
                author.getBirthCountryPortuguese(),
                author.getBirthCountryEnglish(),
                author.getBirthCountryGerman(),
                author.getBirthCountryRussian(),
                author.getBirthCountryJapanese());
    }

    public Multilingual toMultilingualDeathCountry(AuthorEntity author) {
        if (author == null)
            return null;
        return new Multilingual(
                author.getDeathCountryFrench(),
                author.getDeathCountrySpanish(),
                author.getDeathCountryItalian(),
                author.getDeathCountryPortuguese(),
                author.getDeathCountryEnglish(),
                author.getDeathCountryGerman(),
                author.getDeathCountryRussian(),
                author.getDeathCountryJapanese());
    }

    public Multilingual toMultilingualWikipediaLink(AuthorEntity author) {
        if (author == null)
            return null;
        return new Multilingual(
                author.getWikipediaLinkFrench(),
                author.getWikipediaLinkSpanish(),
                author.getWikipediaLinkItalian(),
                author.getWikipediaLinkPortuguese(),
                author.getWikipediaLinkEnglish(),
                author.getWikipediaLinkGerman(),
                author.getWikipediaLinkRussian(),
                author.getWikipediaLinkJapanese());
    }

    public MultilingualList toMultilingualListCitizenships(AuthorEntity author) {
        if (author == null)
            return null;
        return new MultilingualList(
                Arrays.asList(author.getCitizenshipsFrench().split(",")),
                Arrays.asList(author.getCitizenshipsSpanish().split(",")),
                Arrays.asList(author.getCitizenshipsItalian().split(",")),
                Arrays.asList(author.getCitizenshipsPortuguese().split(",")),
                Arrays.asList(author.getCitizenshipsEnglish().split(",")),
                Arrays.asList(author.getCitizenshipsGerman().split(",")),
                Arrays.asList(author.getCitizenshipsRussian().split(",")),
                Arrays.asList(author.getCitizenshipsJapanese().split(",")));
    }

    public MultilingualList toMultilingualListOccupations(AuthorEntity author) {
        if (author == null)
            return null;
        return new MultilingualList(
                Arrays.asList(author.getOccupationsFrench().split(",")),
                Arrays.asList(author.getOccupationsSpanish().split(",")),
                Arrays.asList(author.getOccupationsItalian().split(",")),
                Arrays.asList(author.getOccupationsPortuguese().split(",")),
                Arrays.asList(author.getOccupationsEnglish().split(",")),
                Arrays.asList(author.getOccupationsGerman().split(",")),
                Arrays.asList(author.getOccupationsRussian().split(",")),
                Arrays.asList(author.getOccupationsJapanese().split(",")));
    }

    public MultilingualList toMultilingualListLanguages(AuthorEntity author) {
        if (author == null)
            return null;
        return new MultilingualList(
                Arrays.asList(author.getLanguagesFrench().split(",")),
                Arrays.asList(author.getLanguagesSpanish().split(",")),
                Arrays.asList(author.getLanguagesItalian().split(",")),
                Arrays.asList(author.getLanguagesPortuguese().split(",")),
                Arrays.asList(author.getLanguagesEnglish().split(",")),
                Arrays.asList(author.getLanguagesGerman().split(",")),
                Arrays.asList(author.getLanguagesRussian().split(",")),
                Arrays.asList(author.getLanguagesJapanese().split(",")));
    }

    public Multilingual toMultilingualDescription(BookEntity book) {
        if (book == null)
            return null;
        return new Multilingual(
                book.getDescriptionFrench(),
                book.getDescriptionSpanish(),
                book.getDescriptionItalian(),
                book.getDescriptionPortuguese(),
                book.getDescriptionEnglish(),
                book.getDescriptionGerman(),
                book.getDescriptionRussian(),
                book.getDescriptionJapanese());
    }

    public Multilingual toMultilingualTitle(BookEntity book) {
        if (book == null)
            return null;
        return new Multilingual(
                book.getTitleFrench(),
                book.getTitleSpanish(),
                book.getTitleItalian(),
                book.getTitlePortuguese(),
                book.getTitleEnglish(),
                book.getTitleGerman(),
                book.getTitleRussian(),
                book.getTitleJapanese());
    }

    public Multilingual toMultilingualWikipediaLink(BookEntity book) {
        if (book == null)
            return null;
        return new Multilingual(
                book.getWikipediaLinkFrench(),
                book.getWikipediaLinkSpanish(),
                book.getWikipediaLinkItalian(),
                book.getWikipediaLinkPortuguese(),
                book.getWikipediaLinkEnglish(),
                book.getWikipediaLinkGerman(),
                book.getWikipediaLinkRussian(),
                book.getWikipediaLinkJapanese());
    }

    public MultilingualList toMultilingualListTags(BookEntity book) {
        if (book == null)
            return null;
        return new MultilingualList(
                Arrays.asList(book.getTagsFrench().split(",")),
                Arrays.asList(book.getTagsSpanish().split(",")),
                Arrays.asList(book.getTagsItalian().split(",")),
                Arrays.asList(book.getTagsPortuguese().split(",")),
                Arrays.asList(book.getTagsEnglish().split(",")),
                Arrays.asList(book.getTagsGerman().split(",")),
                Arrays.asList(book.getTagsRussian().split(",")),
                Arrays.asList(book.getTagsJapanese().split(",")));
    }

    public void applyMultilingualDescription(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null)
            return;
        entity.setDescriptionFrench(ml.getFrench());
        entity.setDescriptionSpanish(ml.getSpanish());
        entity.setDescriptionItalian(ml.getItalian());
        entity.setDescriptionPortuguese(ml.getPortuguese());
        entity.setDescriptionEnglish(ml.getEnglish());
        entity.setDescriptionGerman(ml.getGerman());
        entity.setDescriptionRussian(ml.getRussian());
        entity.setDescriptionJapanese(ml.getJapanese());
    }

    public void applyMultilingualTitle(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null)
            return;
        entity.setTitleFrench(ml.getFrench());
        entity.setTitleSpanish(ml.getSpanish());
        entity.setTitleItalian(ml.getItalian());
        entity.setTitlePortuguese(ml.getPortuguese());
        entity.setTitleEnglish(ml.getEnglish());
        entity.setTitleGerman(ml.getGerman());
        entity.setTitleRussian(ml.getRussian());
        entity.setTitleJapanese(ml.getJapanese());
    }

    public void applyMultilingualDescription(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null)
            return;
        entity.setDescriptionFrench(ml.getFrench());
        entity.setDescriptionSpanish(ml.getSpanish());
        entity.setDescriptionItalian(ml.getItalian());
        entity.setDescriptionPortuguese(ml.getPortuguese());
        entity.setDescriptionEnglish(ml.getEnglish());
        entity.setDescriptionGerman(ml.getGerman());
        entity.setDescriptionRussian(ml.getRussian());
        entity.setDescriptionJapanese(ml.getJapanese());
    }
}
