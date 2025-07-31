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
                author.getDescriptionDanish(),
                author.getDescriptionSwedish(),
                author.getDescriptionNorwegian(),
                author.getDescriptionRussian(),
                author.getDescriptionJapanese(),
                author.getDescriptionKorean(),
                author.getDescriptionChinese()
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
                book.getDescriptionDanish(),
                book.getDescriptionSwedish(),
                book.getDescriptionNorwegian(),
                book.getDescriptionRussian(),
                book.getDescriptionJapanese(),
                book.getDescriptionKorean(),
                book.getDescriptionChinese()
        );
    }

    public Multilingual toMultilingualTitle(BookEntity book) {
        if (book == null) return null;
        return new Multilingual(
                book.getTranslatedTitleFrench(),
                book.getTranslatedTitleSpanish(),
                book.getTranslatedTitleItalian(),
                book.getTranslatedTitlePortuguese(),
                book.getTranslatedTitleEnglish(),
                book.getTranslatedTitleGerman(),
                book.getTranslatedTitleDanish(),
                book.getTranslatedTitleSwedish(),
                book.getTranslatedTitleNorwegian(),
                book.getTranslatedTitleRussian(),
                book.getTranslatedTitleJapanese(),
                book.getTranslatedTitleKorean(),
                book.getTranslatedTitleChinese()
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
        entity.setDescriptionDanish(ml.getDanish());
        entity.setDescriptionSwedish(ml.getSwedish());
        entity.setDescriptionNorwegian(ml.getNorwegian());
        entity.setDescriptionRussian(ml.getRussian());
        entity.setDescriptionJapanese(ml.getJapanese());
        entity.setDescriptionKorean(ml.getKorean());
        entity.setDescriptionChinese(ml.getChinese());
    }

    public void applyMultilingualDescription(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null) return;
        entity.setDescriptionFrench(ml.getFrench());
        entity.setDescriptionSpanish(ml.getSpanish());
        entity.setDescriptionItalian(ml.getItalian());
        entity.setDescriptionPortuguese(ml.getPortuguese());
        entity.setDescriptionEnglish(ml.getEnglish());
        entity.setDescriptionGerman(ml.getGerman());
        entity.setDescriptionDanish(ml.getDanish());
        entity.setDescriptionSwedish(ml.getSwedish());
        entity.setDescriptionNorwegian(ml.getNorwegian());
        entity.setDescriptionRussian(ml.getRussian());
        entity.setDescriptionJapanese(ml.getJapanese());
        entity.setDescriptionKorean(ml.getKorean());
        entity.setDescriptionChinese(ml.getChinese());
    }

    public void applyMultilingualTitle(Multilingual ml, BookEntity entity) {
        if (ml == null || entity == null) return;
        entity.setTranslatedTitleFrench(ml.getFrench());
        entity.setTranslatedTitleSpanish(ml.getSpanish());
        entity.setTranslatedTitleItalian(ml.getItalian());
        entity.setTranslatedTitlePortuguese(ml.getPortuguese());
        entity.setTranslatedTitleEnglish(ml.getEnglish());
        entity.setTranslatedTitleGerman(ml.getGerman());
        entity.setTranslatedTitleDanish(ml.getDanish());
        entity.setTranslatedTitleSwedish(ml.getSwedish());
        entity.setTranslatedTitleNorwegian(ml.getNorwegian());
        entity.setTranslatedTitleRussian(ml.getRussian());
        entity.setTranslatedTitleJapanese(ml.getJapanese());
        entity.setTranslatedTitleKorean(ml.getKorean());
        entity.setTranslatedTitleChinese(ml.getChinese());
    }
}
