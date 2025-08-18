package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;
import com.github.pemistahl.lingua.api.Language;

import java.util.*;

@Component
public class MyMemoryLangMapper {

    private final Map<Language, String> l2iso;

    public MyMemoryLangMapper() {
        EnumMap<Language, String> m = new EnumMap<>(Language.class);

        m.put(Language.AFRIKAANS, "af");
        m.put(Language.ALBANIAN, "sq");
        m.put(Language.ARABIC, "ar");
        m.put(Language.ARMENIAN, "hy");
        m.put(Language.AZERBAIJANI, "az");
        m.put(Language.BASQUE, "eu");
        m.put(Language.BELARUSIAN, "be");
        m.put(Language.BENGALI, "bn");
        m.put(Language.BOKMAL, "no");       // Bokmål → “no” (Norwegian)
        m.put(Language.BOSNIAN, "bs");
        m.put(Language.BULGARIAN, "bg");
        m.put(Language.CATALAN, "ca");
        m.put(Language.CHINESE, "zh");
        m.put(Language.CROATIAN, "hr");
        m.put(Language.CZECH, "cs");
        m.put(Language.DANISH, "da");
        m.put(Language.DUTCH, "nl");
        m.put(Language.ENGLISH, "en");
        m.put(Language.ESPERANTO, "eo");
        m.put(Language.ESTONIAN, "et");
        m.put(Language.FINNISH, "fi");
        m.put(Language.FRENCH, "fr");
        m.put(Language.GANDA, "lg");       // Luganda
        m.put(Language.GEORGIAN, "ka");
        m.put(Language.GERMAN, "de");
        m.put(Language.GREEK, "el");
        m.put(Language.GUJARATI, "gu");
        m.put(Language.HEBREW, "he");      // sometimes “iw”, but modern ISO uses “he”
        m.put(Language.HINDI, "hi");
        m.put(Language.HUNGARIAN, "hu");
        m.put(Language.ICELANDIC, "is");
        m.put(Language.INDONESIAN, "id");  // formerly “in”
        m.put(Language.IRISH, "ga");
        m.put(Language.ITALIAN, "it");
        m.put(Language.JAPANESE, "ja");
        m.put(Language.KAZAKH, "kk");
        m.put(Language.KOREAN, "ko");
        m.put(Language.LATIN, "la");
        m.put(Language.LATVIAN, "lv");
        m.put(Language.LITHUANIAN, "lt");
        m.put(Language.MACEDONIAN, "mk");
        m.put(Language.MALAY, "ms");
        m.put(Language.MAORI, "mi");
        m.put(Language.MARATHI, "mr");
        m.put(Language.MONGOLIAN, "mn");
        m.put(Language.NYNORSK, "nn");     // Nynorsk
        m.put(Language.PERSIAN, "fa");
        m.put(Language.POLISH, "pl");
        m.put(Language.PORTUGUESE, "pt");
        m.put(Language.PUNJABI, "pa");
        m.put(Language.ROMANIAN, "ro");
        m.put(Language.RUSSIAN, "ru");
        m.put(Language.SERBIAN, "sr");
        m.put(Language.SHONA, "sn");
        m.put(Language.SLOVAK, "sk");
        m.put(Language.SLOVENE, "sl");
        m.put(Language.SOMALI, "so");
        m.put(Language.SOTHO, "st");       // Sesotho
        m.put(Language.SPANISH, "es");
        m.put(Language.SWAHILI, "sw");
        m.put(Language.SWEDISH, "sv");
        m.put(Language.TAGALOG, "tl");     // often “fil” in newer standards, but MyMemory accepts “tl”
        m.put(Language.TAMIL, "ta");
        m.put(Language.TELUGU, "te");
        m.put(Language.THAI, "th");
        m.put(Language.TSONGA, "ts");
        m.put(Language.TSWANA, "tn");
        m.put(Language.TURKISH, "tr");
        m.put(Language.UKRAINIAN, "uk");
        m.put(Language.URDU, "ur");
        m.put(Language.VIETNAMESE, "vi");
        m.put(Language.WELSH, "cy");
        m.put(Language.XHOSA, "xh");
        m.put(Language.YORUBA, "yo");
        m.put(Language.ZULU, "zu");

        this.l2iso = Collections.unmodifiableMap(m);
    }

    /** Lingua → ISO-639-1 code (for MyMemory API) */
    public Optional<String> toIso(Language lingua) {
        return Optional.ofNullable(l2iso.get(lingua));
    }
}
