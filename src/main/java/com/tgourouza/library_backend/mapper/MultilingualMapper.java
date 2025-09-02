package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;

@Component
public class MultilingualMapper {

    public Multilingual toMultilingualDescription(AuthorEntity author) {
        if (author == null) {
            return null;
        }
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
        if (author == null) {
            return null;
        }
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

    public Multilingual toMultilingualWikipediaLink(AuthorEntity author) {
        if (author == null) {
            return null;
        }
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

    public Multilingual toMultilingualDescription(BookEntity book) {
        if (book == null) {
            return null;
        }
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
        if (book == null) {
            return null;
        }
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

    public void applyMultilingualWikipediaLink(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setWikipediaLinkFrench(ml.french());
        entity.setWikipediaLinkSpanish(ml.spanish());
        entity.setWikipediaLinkItalian(ml.italian());
        entity.setWikipediaLinkPortuguese(ml.portuguese());
        entity.setWikipediaLinkEnglish(ml.english());
        entity.setWikipediaLinkGerman(ml.german());
        entity.setWikipediaLinkRussian(ml.russian());
        entity.setWikipediaLinkJapanese(ml.japanese());
    }

    public void applyMultilingualDescription(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setDescriptionFrench(ml.french());
        entity.setDescriptionSpanish(ml.spanish());
        entity.setDescriptionItalian(ml.italian());
        entity.setDescriptionPortuguese(ml.portuguese());
        entity.setDescriptionEnglish(ml.english());
        entity.setDescriptionGerman(ml.german());
        entity.setDescriptionRussian(ml.russian());
        entity.setDescriptionJapanese(ml.japanese());
    }

    public void applyMultilingualShortDescription(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setShortDescriptionFrench(ml.french());
        entity.setShortDescriptionSpanish(ml.spanish());
        entity.setShortDescriptionItalian(ml.italian());
        entity.setShortDescriptionPortuguese(ml.portuguese());
        entity.setShortDescriptionEnglish(ml.english());
        entity.setShortDescriptionGerman(ml.german());
        entity.setShortDescriptionRussian(ml.russian());
        entity.setShortDescriptionJapanese(ml.japanese());
    }

    public void applyMultilingualTitle(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setTitleFrench(ml.french());
        entity.setTitleSpanish(ml.spanish());
        entity.setTitleItalian(ml.italian());
        entity.setTitlePortuguese(ml.portuguese());
        entity.setTitleEnglish(ml.english());
        entity.setTitleGerman(ml.german());
        entity.setTitleRussian(ml.russian());
        entity.setTitleJapanese(ml.japanese());
    }

    public void applyMultilingualDescription(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setDescriptionFrench(ml.french());
        entity.setDescriptionSpanish(ml.spanish());
        entity.setDescriptionItalian(ml.italian());
        entity.setDescriptionPortuguese(ml.portuguese());
        entity.setDescriptionEnglish(ml.english());
        entity.setDescriptionGerman(ml.german());
        entity.setDescriptionRussian(ml.russian());
        entity.setDescriptionJapanese(ml.japanese());
    }
}
