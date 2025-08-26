package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.*;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.MultilingualList;
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

    public Multilingual toMultilingualBirthCountry(AuthorEntity author) {
        if (author == null) {
            return null;
        }
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
        if (author == null) {
            return null;
        }
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

    public MultilingualList toMultilingualListCitizenships(AuthorEntity author) {
        if (author == null) {
            return null;
        }
        return new MultilingualList(
                toList(author.getCitizenshipsFrench()),
                toList(author.getCitizenshipsSpanish()),
                toList(author.getCitizenshipsItalian()),
                toList(author.getCitizenshipsPortuguese()),
                toList(author.getCitizenshipsEnglish()),
                toList(author.getCitizenshipsGerman()),
                toList(author.getCitizenshipsRussian()),
                toList(author.getCitizenshipsJapanese()));
    }

    public MultilingualList toMultilingualListOccupations(AuthorEntity author) {
        if (author == null) {
            return null;
        }
        return new MultilingualList(
                toList(author.getOccupationsFrench()),
                toList(author.getOccupationsSpanish()),
                toList(author.getOccupationsItalian()),
                toList(author.getOccupationsPortuguese()),
                toList(author.getOccupationsEnglish()),
                toList(author.getOccupationsGerman()),
                toList(author.getOccupationsRussian()),
                toList(author.getOccupationsJapanese()));
    }

    public MultilingualList toMultilingualListLanguages(AuthorEntity author) {
        if (author == null) {
            return null;
        }
        return new MultilingualList(
                toList(author.getLanguagesFrench()),
                toList(author.getLanguagesSpanish()),
                toList(author.getLanguagesItalian()),
                toList(author.getLanguagesPortuguese()),
                toList(author.getLanguagesEnglish()),
                toList(author.getLanguagesGerman()),
                toList(author.getLanguagesRussian()),
                toList(author.getLanguagesJapanese()));
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

    public void applyMultilingualBirthCountry(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setBirthCountryFrench(ml.getFrench());
        entity.setBirthCountrySpanish(ml.getSpanish());
        entity.setBirthCountryItalian(ml.getItalian());
        entity.setBirthCountryPortuguese(ml.getPortuguese());
        entity.setBirthCountryEnglish(ml.getEnglish());
        entity.setBirthCountryGerman(ml.getGerman());
        entity.setBirthCountryRussian(ml.getRussian());
        entity.setBirthCountryJapanese(ml.getJapanese());
    }

    public void applyMultilingualDeathCountry(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setDeathCountryFrench(ml.getFrench());
        entity.setDeathCountrySpanish(ml.getSpanish());
        entity.setDeathCountryItalian(ml.getItalian());
        entity.setDeathCountryPortuguese(ml.getPortuguese());
        entity.setDeathCountryEnglish(ml.getEnglish());
        entity.setDeathCountryGerman(ml.getGerman());
        entity.setDeathCountryRussian(ml.getRussian());
        entity.setDeathCountryJapanese(ml.getJapanese());
    }

    public void applyMultilingualCitizenships(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setCitizenshipsFrench(ml.getFrench());
        entity.setCitizenshipsSpanish(ml.getSpanish());
        entity.setCitizenshipsItalian(ml.getItalian());
        entity.setCitizenshipsPortuguese(ml.getPortuguese());
        entity.setCitizenshipsEnglish(ml.getEnglish());
        entity.setCitizenshipsGerman(ml.getGerman());
        entity.setCitizenshipsRussian(ml.getRussian());
        entity.setCitizenshipsJapanese(ml.getJapanese());
    }

    public void applyMultilingualOccupations(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setOccupationsFrench(ml.getFrench());
        entity.setOccupationsSpanish(ml.getSpanish());
        entity.setOccupationsItalian(ml.getItalian());
        entity.setOccupationsPortuguese(ml.getPortuguese());
        entity.setOccupationsEnglish(ml.getEnglish());
        entity.setOccupationsGerman(ml.getGerman());
        entity.setOccupationsRussian(ml.getRussian());
        entity.setOccupationsJapanese(ml.getJapanese());
    }

    public void applyMultilingualLanguages(Multilingual ml, AuthorEntity entity) {
        if (ml == null || entity == null) {
            return;
        }
        entity.setLanguagesFrench(ml.getFrench());
        entity.setLanguagesSpanish(ml.getSpanish());
        entity.setLanguagesItalian(ml.getItalian());
        entity.setLanguagesPortuguese(ml.getPortuguese());
        entity.setLanguagesEnglish(ml.getEnglish());
        entity.setLanguagesGerman(ml.getGerman());
        entity.setLanguagesRussian(ml.getRussian());
        entity.setLanguagesJapanese(ml.getJapanese());
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
