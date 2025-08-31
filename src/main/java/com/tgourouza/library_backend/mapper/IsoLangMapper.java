package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.constant.Language;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IsoLangMapper {

    private final Map<Language, String> l2iso;
    private final Map<String, Language> iso2lang;

    public IsoLangMapper() {
        EnumMap<Language, String> m = new EnumMap<>(Language.class);

        m.put(Language.ENGLISH, "en");  m.put(Language.FRENCH, "fr");
        m.put(Language.GERMAN, "de");   m.put(Language.SPANISH, "es");
        m.put(Language.ITALIAN, "it");  m.put(Language.PORTUGUESE, "pt");
        m.put(Language.RUSSIAN, "ru");  m.put(Language.JAPANESE, "ja");
        m.put(Language.CHINESE, "zh");  m.put(Language.ARABIC, "ar");
        m.put(Language.TURKISH, "tr");  m.put(Language.UKRAINIAN, "uk");
        m.put(Language.VIETNAMESE, "vi"); m.put(Language.POLISH, "pl");
        m.put(Language.CZECH, "cs");    m.put(Language.DUTCH, "nl");
        m.put(Language.GREEK, "el");    m.put(Language.ROMANIAN, "ro");
        m.put(Language.HUNGARIAN, "hu"); m.put(Language.FINNISH, "fi");
        m.put(Language.SWEDISH, "sv");  m.put(Language.DANISH, "da");
        m.put(Language.HEBREW, "he");   m.put(Language.HINDI, "hi");
        m.put(Language.INDONESIAN, "id"); m.put(Language.KOREAN, "ko");
        m.put(Language.THAI, "th");     m.put(Language.CATALAN, "ca");
        m.put(Language.BULGARIAN, "bg"); m.put(Language.SLOVAK, "sk");
        m.put(Language.SLOVENE, "sl");  m.put(Language.LITHUANIAN, "lt");
        m.put(Language.LATVIAN, "lv");  m.put(Language.ESTONIAN, "et");
        m.put(Language.CROATIAN, "hr"); m.put(Language.SERBIAN, "sr");
        m.put(Language.BOSNIAN, "bs");  m.put(Language.MACEDONIAN, "mk");
        m.put(Language.ALBANIAN, "sq"); m.put(Language.ARMENIAN, "hy");
        m.put(Language.GEORGIAN, "ka"); m.put(Language.BELARUSIAN, "be");
        m.put(Language.PERSIAN, "fa");  m.put(Language.URDU, "ur");
        m.put(Language.BENGALI, "bn");  m.put(Language.TAMIL, "ta");
        m.put(Language.TELUGU, "te");   m.put(Language.MALAY, "ms");
        m.put(Language.TAGALOG, "tl");  m.put(Language.SWAHILI, "sw");
        m.put(Language.AFRIKAANS, "af"); m.put(Language.BASQUE, "eu");
        m.put(Language.ICELANDIC, "is"); m.put(Language.IRISH, "ga");
        m.put(Language.MAORI, "mi");    m.put(Language.MONGOLIAN, "mn");
        m.put(Language.KAZAKH, "kk");   m.put(Language.SHONA, "sn");
        m.put(Language.SOMALI, "so");   m.put(Language.SOTHO, "st");
        m.put(Language.TSONGA, "ts");   m.put(Language.TSWANA, "tn");
        m.put(Language.WELSH, "cy");    m.put(Language.XHOSA, "xh");
        m.put(Language.YORUBA, "yo");   m.put(Language.ZULU, "zu");
        m.put(Language.ESPERANTO, "eo"); m.put(Language.NYNORSK, "nn");
        m.put(Language.BOKMAL, "no");   m.put(Language.AZERBAIJANI, "az");
        m.put(Language.GUJARATI, "gu");
        // langues présentes dans MyMemory mapper:
        m.put(Language.LATIN, "la");
        m.put(Language.MARATHI, "mr");
        m.put(Language.PUNJABI, "pa");
        m.put(Language.GANDA, "lg");
        this.l2iso = Collections.unmodifiableMap(m);

        Map<String, Language> tmp = new HashMap<>();
        for (Language lang : Language.values()) {
            tmp.put(toIso(lang), lang);
        }
        this.iso2lang = Collections.unmodifiableMap(tmp);
    }

    /** Language → ISO-639-1 */
    public String toIso(Language language) {
        return l2iso.get(language);
    }

    /** ISO-639-1 -> Language */
    public Language toLanguage(String code) {
        if (code == null) return Language.UNKNOWN;
        return iso2lang.get(code);
    }
}
