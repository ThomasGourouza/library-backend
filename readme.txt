# one line
rm src/main/resources/openlibrary-truststore.jks && \
rm src/main/resources/mymemory-trust.jks && \
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem && \
openssl s_client -connect api.mymemory.translated.net:443 -showcerts </dev/null | \
  openssl x509 -outform PEM > corp-or-site-ca.pem && \
keytool -importcert -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore src/main/resources/openlibrary-truststore.jks \
  -storepass openlibrary123 -noprompt && \
keytool -importcert -alias mymemory-ca \
  -file corp-or-site-ca.pem \
  -keystore src/main/resources/mymemory-trust.jks \
  -storepass mymemory123 -noprompt && \
rm openlibrary-chain.pem && \
rm corp-or-site-ca.pem


---
rm -f src/main/resources/wikidata-truststore.jks && \
openssl s_client -showcerts -connect query.wikidata.org:443 </dev/null 2>/dev/null \
  | awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > wikidata-chain.pem && \
awk '
  /BEGIN CERTIFICATE/ { n++; fn=sprintf("cert-%02d.pem", n) }
  { print > fn }
  /END CERTIFICATE/ { close(fn) }
' wikidata-chain.pem && \
i=0
for cert in cert-*.pem; do
  alias="wikidata-ca-$i"
  keytool -importcert \
    -alias "$alias" \
    -file "$cert" \
    -keystore src/main/resources/wikidata-truststore.jks \
    -storepass wikidata123 -noprompt
  i=$((i+1))
done && \
rm -f wikidata-chain.pem cert-*.pem

---

# get the server chain
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem

# import the *root or intermediate CA* you need (better: the root)
keytool -importcert -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore src/main/resources/openlibrary-truststore.jks \
  -storepass openlibrary123 -noprompt

# one line
rm src/main/resources/openlibrary-truststore.jks && \
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem && \
keytool -importcert -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore src/main/resources/openlibrary-truststore.jks \
  -storepass openlibrary123 -noprompt && \
rm openlibrary-chain.pem

-------

# 1 Grab the cert chain actually presented to you (proxy or site)
openssl s_client -connect api.mymemory.translated.net:443 -showcerts </dev/null | \
  openssl x509 -outform PEM > corp-or-site-ca.pem

# 2 Import into a JKS (put JKS under src/main/resources/)
keytool -importcert -alias mymemory-ca \
  -file corp-or-site-ca.pem \
  -keystore src/main/resources/mymemory-trust.jks \
  -storepass mymemory123 -noprompt

# one line
rm src/main/resources/mymemory-trust.jks && \
openssl s_client -connect api.mymemory.translated.net:443 -showcerts </dev/null | \
  openssl x509 -outform PEM > corp-or-site-ca.pem && \
keytool -importcert -alias mymemory-ca \
  -file corp-or-site-ca.pem \
  -keystore src/main/resources/mymemory-trust.jks \
  -storepass mymemory123 -noprompt && \
rm corp-or-site-ca.pem


-------

### nllb translation server

#(Use --build only when you changed something in the build context (e.g., Dockerfile, server.py, requirements.txt, etc.))
docker-compose up -d --build nllb

#(If nothing or only docker-compose.yml changed)
docker-compose up -d

#Wait for healthy:
docker ps --filter name=nllb-translate

curl http://localhost:18000/health

#Test translate:
curl -H "Content-Type: application/json" \
  -d '{"text":"Hello","source":"eng_Latn","target":"fra_Latn"}' \
  http://localhost:18000/translate


NLLB:
[
    "ace_Arab",
    "ace_Latn",
    "acm_Arab",
    "acq_Arab",
    "aeb_Arab",
    "afr_Latn",
    "ajp_Arab",
    "aka_Latn",
    "amh_Ethi",
    "apc_Arab",
    "arb_Arab",
    "ars_Arab",
    "ary_Arab",
    "arz_Arab",
    "asm_Beng",
    "ast_Latn",
    "awa_Deva",
    "ayr_Latn",
    "azb_Arab",
    "azj_Latn",
    "bak_Cyrl",
    "bam_Latn",
    "ban_Latn",
    "bel_Cyrl",
    "bem_Latn",
    "ben_Beng",
    "bho_Deva",
    "bjn_Arab",
    "bjn_Latn",
    "bod_Tibt",
    "bos_Latn",
    "bug_Latn",
    "bul_Cyrl",
    "cat_Latn",
    "ceb_Latn",
    "ces_Latn",
    "cjk_Latn",
    "ckb_Arab",
    "crh_Latn",
    "cym_Latn",
    "dan_Latn",
    "deu_Latn",
    "dik_Latn",
    "dyu_Latn",
    "dzo_Tibt",
    "ell_Grek",
    "eng_Latn",
    "epo_Latn",
    "est_Latn",
    "eus_Latn",
    "ewe_Latn",
    "fao_Latn",
    "pes_Arab",
    "fij_Latn",
    "fin_Latn",
    "fon_Latn",
    "fra_Latn",
    "fur_Latn",
    "fuv_Latn",
    "gla_Latn",
    "gle_Latn",
    "glg_Latn",
    "grn_Latn",
    "guj_Gujr",
    "hat_Latn",
    "hau_Latn",
    "heb_Hebr",
    "hin_Deva",
    "hne_Deva",
    "hrv_Latn",
    "hun_Latn",
    "hye_Armn",
    "ibo_Latn",
    "ilo_Latn",
    "ind_Latn",
    "isl_Latn",
    "ita_Latn",
    "jav_Latn",
    "jpn_Jpan",
    "kab_Latn",
    "kac_Latn",
    "kam_Latn",
    "kan_Knda",
    "kas_Arab",
    "kas_Deva",
    "kat_Geor",
    "knc_Arab",
    "knc_Latn",
    "kaz_Cyrl",
    "kbp_Latn",
    "kea_Latn",
    "khm_Khmr",
    "kik_Latn",
    "kin_Latn",
    "kir_Cyrl",
    "kmb_Latn",
    "kon_Latn",
    "kor_Hang",
    "kmr_Latn",
    "lao_Laoo",
    "lvs_Latn",
    "lij_Latn",
    "lim_Latn",
    "lin_Latn",
    "lit_Latn",
    "lmo_Latn",
    "ltg_Latn",
    "ltz_Latn",
    "lua_Latn",
    "lug_Latn",
    "luo_Latn",
    "lus_Latn",
    "mag_Deva",
    "mai_Deva",
    "mal_Mlym",
    "mar_Deva",
    "min_Latn",
    "mkd_Cyrl",
    "plt_Latn",
    "mlt_Latn",
    "mni_Beng",
    "khk_Cyrl",
    "mos_Latn",
    "mri_Latn",
    "zsm_Latn",
    "mya_Mymr",
    "nld_Latn",
    "nno_Latn",
    "nob_Latn",
    "npi_Deva",
    "nso_Latn",
    "nus_Latn",
    "nya_Latn",
    "oci_Latn",
    "gaz_Latn",
    "ory_Orya",
    "pag_Latn",
    "pan_Guru",
    "pap_Latn",
    "pol_Latn",
    "por_Latn",
    "prs_Arab",
    "pbt_Arab",
    "quy_Latn",
    "ron_Latn",
    "run_Latn",
    "rus_Cyrl",
    "sag_Latn",
    "san_Deva",
    "sat_Beng",
    "scn_Latn",
    "shn_Mymr",
    "sin_Sinh",
    "slk_Latn",
    "slv_Latn",
    "smo_Latn",
    "sna_Latn",
    "snd_Arab",
    "som_Latn",
    "sot_Latn",
    "spa_Latn",
    "als_Latn",
    "srd_Latn",
    "srp_Cyrl",
    "ssw_Latn",
    "sun_Latn",
    "swe_Latn",
    "swh_Latn",
    "szl_Latn",
    "tam_Taml",
    "tat_Cyrl",
    "tel_Telu",
    "tgk_Cyrl",
    "tgl_Latn",
    "tha_Thai",
    "tir_Ethi",
    "taq_Latn",
    "taq_Tfng",
    "tpi_Latn",
    "tsn_Latn",
    "tso_Latn",
    "tuk_Latn",
    "tum_Latn",
    "tur_Latn",
    "twi_Latn",
    "tzm_Tfng",
    "uig_Arab",
    "ukr_Cyrl",
    "umb_Latn",
    "urd_Arab",
    "uzn_Latn",
    "vec_Latn",
    "vie_Latn",
    "war_Latn",
    "wol_Latn",
    "xho_Latn",
    "ydd_Hebr",
    "yor_Latn",
    "yue_Hant",
    "zho_Hans",
    "zho_Hant",
    "zul_Latn"
]

mymemory:
2 LETTER ISO (fr, en, ...)

pemistahl.lingua.api:
AFRIKAANS,
ALBANIAN,
ARABIC,
ARMENIAN,
AZERBAIJANI,
BASQUE,
BELARUSIAN,
BENGALI,
BOKMAL,
BOSNIAN,
BULGARIAN,
CATALAN,
CHINESE,
CROATIAN,
CZECH,
DANISH,
DUTCH,
ENGLISH,
ESPERANTO,
ESTONIAN,
FINNISH,
FRENCH,
GANDA,
GEORGIAN,
GERMAN,
GREEK,
GUJARATI,
HEBREW,
HINDI,
HUNGARIAN,
ICELANDIC,
INDONESIAN,
IRISH,
ITALIAN,
JAPANESE,
KAZAKH,
KOREAN,
LATIN,
LATVIAN,
LITHUANIAN,
MACEDONIAN,
MALAY,
MAORI,
MARATHI,
MONGOLIAN,
NYNORSK,
PERSIAN,
POLISH,
PORTUGUESE,
PUNJABI,
ROMANIAN,
RUSSIAN,
SERBIAN,
SHONA,
SLOVAK,
SLOVENE,
SOMALI,
SOTHO,
SPANISH,
SWAHILI,
SWEDISH,
TAGALOG,
TAMIL,
TELUGU,
THAI,
TSONGA,
TSWANA,
TURKISH,
UKRAINIAN,
URDU,
VIETNAMESE,
WELSH,
XHOSA,
YORUBA,
ZULU,
UNKNOWN;
