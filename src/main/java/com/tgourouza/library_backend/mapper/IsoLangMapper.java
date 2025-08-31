package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.constant.DataLanguage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IsoLangMapper {

    private final Map<DataLanguage, String> l2iso;
    private final Map<String, DataLanguage> iso2lang;

    public IsoLangMapper() {
        EnumMap<DataLanguage, String> m = new EnumMap<>(DataLanguage.class);

        m.put(DataLanguage.ENGLISH, "en");  m.put(DataLanguage.FRENCH, "fr");
        m.put(DataLanguage.GERMAN, "de");   m.put(DataLanguage.SPANISH, "es");
        m.put(DataLanguage.ITALIAN, "it");  m.put(DataLanguage.PORTUGUESE, "pt");
        m.put(DataLanguage.RUSSIAN, "ru");  m.put(DataLanguage.JAPANESE, "ja");
        m.put(DataLanguage.CHINESE, "zh");  m.put(DataLanguage.ARABIC, "ar");
        m.put(DataLanguage.TURKISH, "tr");  m.put(DataLanguage.UKRAINIAN, "uk");
        m.put(DataLanguage.VIETNAMESE, "vi"); m.put(DataLanguage.POLISH, "pl");
        m.put(DataLanguage.CZECH, "cs");    m.put(DataLanguage.DUTCH, "nl");
        m.put(DataLanguage.GREEK, "el");    m.put(DataLanguage.ROMANIAN, "ro");
        m.put(DataLanguage.HUNGARIAN, "hu"); m.put(DataLanguage.FINNISH, "fi");
        m.put(DataLanguage.SWEDISH, "sv");  m.put(DataLanguage.DANISH, "da");
        m.put(DataLanguage.HEBREW, "he");   m.put(DataLanguage.HINDI, "hi");
        m.put(DataLanguage.INDONESIAN, "id"); m.put(DataLanguage.KOREAN, "ko");
        m.put(DataLanguage.THAI, "th");     m.put(DataLanguage.CATALAN, "ca");
        m.put(DataLanguage.BULGARIAN, "bg"); m.put(DataLanguage.SLOVAK, "sk");
        m.put(DataLanguage.SLOVENE, "sl");  m.put(DataLanguage.LITHUANIAN, "lt");
        m.put(DataLanguage.LATVIAN, "lv");  m.put(DataLanguage.ESTONIAN, "et");
        m.put(DataLanguage.CROATIAN, "hr"); m.put(DataLanguage.SERBIAN, "sr");
        m.put(DataLanguage.BOSNIAN, "bs");  m.put(DataLanguage.MACEDONIAN, "mk");
        m.put(DataLanguage.ALBANIAN, "sq"); m.put(DataLanguage.ARMENIAN, "hy");
        m.put(DataLanguage.GEORGIAN, "ka"); m.put(DataLanguage.BELARUSIAN, "be");
        m.put(DataLanguage.PERSIAN, "fa");  m.put(DataLanguage.URDU, "ur");
        m.put(DataLanguage.BENGALI, "bn");  m.put(DataLanguage.TAMIL, "ta");
        m.put(DataLanguage.TELUGU, "te");   m.put(DataLanguage.MALAY, "ms");
        m.put(DataLanguage.TAGALOG, "tl");  m.put(DataLanguage.SWAHILI, "sw");
        m.put(DataLanguage.AFRIKAANS, "af"); m.put(DataLanguage.BASQUE, "eu");
        m.put(DataLanguage.ICELANDIC, "is"); m.put(DataLanguage.IRISH, "ga");
        m.put(DataLanguage.MAORI, "mi");    m.put(DataLanguage.MONGOLIAN, "mn");
        m.put(DataLanguage.KAZAKH, "kk");   m.put(DataLanguage.SHONA, "sn");
        m.put(DataLanguage.SOMALI, "so");   m.put(DataLanguage.SOTHO, "st");
        m.put(DataLanguage.TSONGA, "ts");   m.put(DataLanguage.TSWANA, "tn");
        m.put(DataLanguage.WELSH, "cy");    m.put(DataLanguage.XHOSA, "xh");
        m.put(DataLanguage.YORUBA, "yo");   m.put(DataLanguage.ZULU, "zu");
        m.put(DataLanguage.ESPERANTO, "eo"); m.put(DataLanguage.NYNORSK, "nn");
        m.put(DataLanguage.BOKMAL, "no");   m.put(DataLanguage.AZERBAIJANI, "az");
        m.put(DataLanguage.GUJARATI, "gu");
        // langues présentes dans MyMemory mapper:
        m.put(DataLanguage.LATIN, "la");
        m.put(DataLanguage.MARATHI, "mr");
        m.put(DataLanguage.PUNJABI, "pa");
        m.put(DataLanguage.GANDA, "lg");
        this.l2iso = Collections.unmodifiableMap(m);

        Map<String, DataLanguage> tmp = new HashMap<>();
        for (DataLanguage lang : DataLanguage.values()) {
            tmp.put(toIso(lang), lang);
        }
        this.iso2lang = Collections.unmodifiableMap(tmp);
    }

    /** DataLanguage → ISO-639-1 */
    public String toIso(DataLanguage dataLanguage) {
        return l2iso.get(dataLanguage);
    }

    /** ISO-639-1 -> DataLanguage */
    public DataLanguage toLanguage(String code) {
        if (code == null) return DataLanguage.UNKNOWN;
        return iso2lang.get(code);
    }
}
