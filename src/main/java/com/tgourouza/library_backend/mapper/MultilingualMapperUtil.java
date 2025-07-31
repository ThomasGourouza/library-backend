package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class MultilingualMapperUtil {

    public Multilingual toMultilingualDescription(AuthorEntity author) {
        if (author == null) return null;
        return new Multilingual(
                author.getDescriptionFrench(),
                author.getDescriptionSpanish(),
                author.getDescriptionItalian(),
                author.getDescriptionPortuguese(),
                author.getDescriptionEnglish(),
                author.getDescriptionGerman(),
                author.getDescriptionRussian(),
                author.getDescriptionJapanese()
        );
    }

    public Multilingual toMultilingualDescription(BookEntity book) {
        if (book == null) return null;
        return new Multilingual(
                book.getDescriptionFrench(),
                book.getDescriptionSpanish(),
                book.getDescriptionItalian(),
                book.getDescriptionPortuguese(),
                book.getDescriptionEnglish(),
                book.getDescriptionGerman(),
                book.getDescriptionRussian(),
                book.getDescriptionJapanese()
        );
    }

    public Multilingual toMultilingualTitle(BookEntity book) {
        if (book == null) return null;
        return new Multilingual(
                book.getTitleFrench(),
                book.getTitleSpanish(),
                book.getTitleItalian(),
                book.getTitlePortuguese(),
                book.getTitleEnglish(),
                book.getTitleGerman(),
                book.getTitleRussian(),
                book.getTitleJapanese()
        );
    }

    public void applyMultilingualDescription(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) return;
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
        if (ml == null || entity == null) return;
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
        if (ml == null || entity == null) return;
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
