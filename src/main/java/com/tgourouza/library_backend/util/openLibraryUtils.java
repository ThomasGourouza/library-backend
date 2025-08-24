package com.tgourouza.library_backend.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;

public class openLibraryUtils {

    public static String text(JsonNode node, String field) {
        if (node == null || node.isMissingNode()) {
            return "";
        }
        JsonNode n = node.path(field);
        if (n.isMissingNode()) {
            return "";
        }
        if (n.isTextual()) {
            return n.asText("");
        }
        // description/bio can be object { "value": "..." }
        String val = n.path("value").asText("");
        return val == null ? "" : val;
    }

    public static HashSet<String> mergeJsonArraysToSet(JsonNode... arrays) {
        HashSet<String> out = new HashSet<>();
        if (arrays == null) {
            return out;
        }

        for (JsonNode arr : arrays) {
            if (arr == null || !arr.isArray()) {
                continue;
            }
            for (JsonNode n : arr) {
                String s = n.asText("");
                if (!s.isBlank()) {
                    out.add(s.trim());
                }
            }
        }
        return out;
    }

    public static String lastPathSegment(String path) {
        if (path == null) {
            return "";
        }
        int i = path.lastIndexOf('/');
        return (i >= 0 && i + 1 < path.length()) ? path.substring(i + 1) : path;
    }

    public static int parseYear(String s) {
        if (s == null) {
            return 0;
        }
        // grab leading 4-digit year if present
        for (int i = 0; i + 3 < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)) && Character.isDigit(s.charAt(i + 1))
                    && Character.isDigit(s.charAt(i + 2)) && Character.isDigit(s.charAt(i + 3))) {
                try {
                    return Integer.parseInt(s.substring(i, i + 4));
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return 0;
    }

    public static String readDescription(JsonNode work) {
        // "description" may be string or {"value": "..."}
        String d = text(work, "description");
        return d == null ? "" : d;
    }

    public static String readWikipediaLink(JsonNode node, String originalTitle, String language) {
        // direct "wikipedia" field
        String wiki = text(node, "wikipedia");
        if (!wiki.isBlank()) {
            return wiki;
        }

        // links: [ { title, url }, ... ]
        JsonNode links = node.path("links");
        if (links.isArray()) {
            for (JsonNode l : links) {
                String url = l.path("url").asText("");
                if (url.contains("wikipedia.org")) {
                    return url;
                }
            }
        }
        // built link
        String code = resolveWikipediaLangCode(language); // e.g., "fr", "en", ...
        String encodedTitle = encodeWikipediaTitle(originalTitle); // spaces→_, UTF-8 encoding
        return "https://" + code + ".wikipedia.org/wiki/" + encodedTitle;
    }

    public static String coverImage(int id, char size) {
        // b/id/{id}-{S|M|L}.jpg
        return "https://covers.openlibrary.org/b/id/" + id + "-" + size + ".jpg";
    }

    public static String getLanguage(String text) {
        LanguageDetector detector = LanguageDetectorBuilder.fromAllLanguages().build();
        return detector.detectLanguageOf(text).toString();
    }

    /* ============================== Author utils ============================== */
    public static String readBio(JsonNode author) {
        String bio = text(author, "bio");
        return bio == null ? "" : bio;
    }

    public static String authorImage(int id, char size) {
        // a/id/{id}-{S|M|L}.jpg
        return "https://covers.openlibrary.org/a/id/" + id + "-" + size + ".jpg";
    }

    /*
     * ============================== Wikipedia utils ==============================
     */
    private static String resolveWikipediaLangCode(String language) {
        if (language == null || language.isBlank()) {
            return "en";
        }
        String key = language.trim().toLowerCase(Locale.ROOT);

        // direct hit on name/synonym
        String code = LANGUAGE_TO_WIKI.get(key);
        if (code != null) {
            return code;
        }

        // if caller already passed a 2-letter code we support, accept it
        if (key.length() == 2 && LANGUAGE_TO_WIKI.containsValue(key)) {
            return key;
        }

        return "en"; // sensible default
    }

    private static String encodeWikipediaTitle(String title) {
        // Replace whitespace with underscores, then URL-encode non-ASCII/special chars
        String underscored = title.trim().replaceAll("\\s+", "_");
        try {
            String enc = URLEncoder.encode(underscored, StandardCharsets.UTF_8);
            // URLEncoder encodes spaces as '+', keep underscores
            return enc.replace("+", "_");
        } catch (Exception e) {
            return underscored;
        }
    }

    /**
     * Map common language names/synonyms → Wikipedia subdomain (ISO 639-1).
     * Covers ~30 most spoken languages globally.
     */
    private static final Map<String, String> LANGUAGE_TO_WIKI = createWikipediaLangMap();

    private static Map<String, String> createWikipediaLangMap() {
        Map<String, String> m = new HashMap<>();

        // TODO: check in Language enum from pemistahl.lingua
        m.put("english", "en");
        m.put("chinese", "zh");
        m.put("mandarin", "zh");
        m.put("hindi", "hi");
        m.put("spanish", "es");
        m.put("french", "fr");
        m.put("arabic", "ar");
        m.put("bengali", "bn");
        m.put("portuguese", "pt");
        m.put("russian", "ru");

        m.put("urdu", "ur");
        m.put("indonesian", "id");
        m.put("bahasa indonesia", "id");
        m.put("german", "de");
        m.put("japanese", "ja");
        m.put("swahili", "sw");
        m.put("marathi", "mr");
        m.put("telugu", "te");
        m.put("turkish", "tr");
        m.put("korean", "ko");

        m.put("vietnamese", "vi");
        m.put("tamil", "ta");
        m.put("italian", "it");
        m.put("persian", "fa");
        m.put("farsi", "fa");
        m.put("punjabi", "pa");
        m.put("gujarati", "gu");
        m.put("polish", "pl");
        m.put("ukrainian", "uk");
        m.put("dutch", "nl");
        m.put("thai", "th");

        m.put("tagalog", "tl");
        m.put("filipino", "tl");

        return m;
    }
}
