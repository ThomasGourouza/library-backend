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
        entity.setWikipediaLinkFrench(ml.getFrench());
        entity.setWikipediaLinkSpanish(ml.getSpanish());
        entity.setWikipediaLinkItalian(ml.getItalian());
        entity.setWikipediaLinkPortuguese(ml.getPortuguese());
        entity.setWikipediaLinkEnglish(ml.getEnglish());
        entity.setWikipediaLinkGerman(ml.getGerman());
        entity.setWikipediaLinkRussian(ml.getRussian());
        entity.setWikipediaLinkJapanese(ml.getJapanese());
    }

    public void applyMultilingualDescription(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setDescriptionFrench(ml.getFrench());
        entity.setDescriptionSpanish(ml.getSpanish());
        entity.setDescriptionItalian(ml.getItalian());
        entity.setDescriptionPortuguese(ml.getPortuguese());
        entity.setDescriptionEnglish(ml.getEnglish());
        entity.setDescriptionGerman(ml.getGerman());
        entity.setDescriptionRussian(ml.getRussian());
        entity.setDescriptionJapanese(ml.getJapanese());
    }

    public void applyMultilingualShortDescription(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setShortDescriptionFrench(ml.getFrench());
        entity.setShortDescriptionSpanish(ml.getSpanish());
        entity.setShortDescriptionItalian(ml.getItalian());
        entity.setShortDescriptionPortuguese(ml.getPortuguese());
        entity.setShortDescriptionEnglish(ml.getEnglish());
        entity.setShortDescriptionGerman(ml.getGerman());
        entity.setShortDescriptionRussian(ml.getRussian());
        entity.setShortDescriptionJapanese(ml.getJapanese());
    }

    public void applyMultilingualTitle(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
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
        if (ml == null || entity == null) {
            return;
        }
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
