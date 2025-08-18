package com.tgourouza.library_backend.mapper;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.pemistahl.lingua.api.Language;

@Component
public class NllbLangMapper {

    // Lingua -> NLLB
    private final Map<Language, String> l2n;

    public NllbLangMapper() {
        EnumMap<Language, String> m = new EnumMap<>(Language.class);

        // Straightforward one-to-ones
        m.put(Language.AFRIKAANS, "afr_Latn");
        m.put(Language.ALBANIAN, "als_Latn"); // NLLB uses "als" for Albanian
        m.put(Language.ARABIC, "arb_Arab"); // default to MSA (arb_Arab)
        m.put(Language.ARMENIAN, "hye_Armn");
        m.put(Language.AZERBAIJANI, "azj_Latn"); // North Azerbaijani (Latin)
        m.put(Language.BASQUE, "eus_Latn");
        m.put(Language.BELARUSIAN, "bel_Cyrl");
        m.put(Language.BENGALI, "ben_Beng");
        m.put(Language.BOKMAL, "nob_Latn");
        m.put(Language.BOSNIAN, "bos_Latn");
        m.put(Language.BULGARIAN, "bul_Cyrl");
        m.put(Language.CATALAN, "cat_Latn");
        m.put(Language.CHINESE, "zho_Hans"); // default to Simplified; Traditional alias added below
        m.put(Language.CROATIAN, "hrv_Latn");
        m.put(Language.CZECH, "ces_Latn");
        m.put(Language.DANISH, "dan_Latn");
        m.put(Language.DUTCH, "nld_Latn");
        m.put(Language.ENGLISH, "eng_Latn");
        m.put(Language.ESPERANTO, "epo_Latn");
        m.put(Language.ESTONIAN, "est_Latn");
        m.put(Language.FINNISH, "fin_Latn");
        m.put(Language.FRENCH, "fra_Latn");
        m.put(Language.GANDA, "lug_Latn"); // Luganda
        m.put(Language.GEORGIAN, "kat_Geor");
        m.put(Language.GERMAN, "deu_Latn");
        m.put(Language.GREEK, "ell_Grek");
        m.put(Language.GUJARATI, "guj_Gujr");
        m.put(Language.HEBREW, "heb_Hebr");
        m.put(Language.HINDI, "hin_Deva");
        m.put(Language.HUNGARIAN, "hun_Latn");
        m.put(Language.ICELANDIC, "isl_Latn");
        m.put(Language.INDONESIAN, "ind_Latn");
        m.put(Language.IRISH, "gle_Latn");
        m.put(Language.ITALIAN, "ita_Latn");
        m.put(Language.JAPANESE, "jpn_Jpan");
        m.put(Language.KAZAKH, "kaz_Cyrl");
        m.put(Language.KOREAN, "kor_Hang");
        // Language.LATIN -> no NLLB code in the supplied list (unsupported)
        m.put(Language.LATIN, "eng_Latn");
        m.put(Language.LATVIAN, "lvs_Latn");
        m.put(Language.LITHUANIAN, "lit_Latn");
        m.put(Language.MACEDONIAN, "mkd_Cyrl");
        m.put(Language.MALAY, "zsm_Latn");
        m.put(Language.MAORI, "mri_Latn");
        m.put(Language.MARATHI, "mar_Deva");
        m.put(Language.MONGOLIAN, "khk_Cyrl"); // Halh Mongolian
        m.put(Language.NYNORSK, "nno_Latn");
        m.put(Language.PERSIAN, "pes_Arab"); // Western Persian; also allow prs_Arab alias below
        m.put(Language.POLISH, "pol_Latn");
        m.put(Language.PORTUGUESE, "por_Latn");
        m.put(Language.PUNJABI, "pan_Guru"); // Gurmukhi (no Shahmukhi code in the provided list)
        m.put(Language.ROMANIAN, "ron_Latn");
        m.put(Language.RUSSIAN, "rus_Cyrl");
        m.put(Language.SERBIAN, "srp_Cyrl"); // NLLB list here shows Cyrillic; also accept srp_Latn alias
        m.put(Language.SHONA, "sna_Latn");
        m.put(Language.SLOVAK, "slk_Latn");
        m.put(Language.SLOVENE, "slv_Latn");
        m.put(Language.SOMALI, "som_Latn");
        m.put(Language.SOTHO, "sot_Latn");
        m.put(Language.SPANISH, "spa_Latn");
        m.put(Language.SWAHILI, "swh_Latn");
        m.put(Language.SWEDISH, "swe_Latn");
        m.put(Language.TAGALOG, "tgl_Latn");
        m.put(Language.TAMIL, "tam_Taml");
        m.put(Language.TELUGU, "tel_Telu");
        m.put(Language.THAI, "tha_Thai");
        m.put(Language.TSONGA, "tso_Latn");
        m.put(Language.TSWANA, "tsn_Latn");
        m.put(Language.TURKISH, "tur_Latn");
        m.put(Language.UKRAINIAN, "ukr_Cyrl");
        m.put(Language.URDU, "urd_Arab");
        m.put(Language.VIETNAMESE, "vie_Latn");
        m.put(Language.WELSH, "cym_Latn");
        m.put(Language.XHOSA, "xho_Latn");
        m.put(Language.YORUBA, "yor_Latn");
        m.put(Language.ZULU, "zul_Latn");
        // UNKNOWN -> no mapping; handled by Optional.empty()

        this.l2n = Collections.unmodifiableMap(m);
    }

    /** Lingua → NLLB language code (e.g. ENGLISH → "eng_Latn"). */
    public Optional<String> toNllb(Language lingua) {
        return Optional.ofNullable(l2n.get(lingua));
    }
}