package com.tgourouza.library_backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class openLibraryUtils {

    public static String text(JsonNode node, String field) {
        if (node == null || node.isMissingNode())
            return "";
        JsonNode n = node.path(field);
        if (n.isMissingNode())
            return "";
        if (n.isTextual())
            return n.asText("");
        // description/bio can be object { "value": "..." }
        String val = n.path("value").asText("");
        return val == null ? "" : val;
    }

    public static String joinCsv(JsonNode arr) {
        if (!arr.isArray() || arr.size() == 0)
            return "";
        List<String> list = new ArrayList<>();
        for (JsonNode n : arr) {
            String s = n.asText("");
            if (s != null && !s.isBlank())
                list.add(s.trim());
        }
        // de-dup but keep order
        LinkedHashSet<String> set = new LinkedHashSet<>(list);
        return String.join(",", set);
    }

    public static String firstNonEmpty(String a, String b) {
        if (a != null && !a.isBlank())
            return a;
        return b == null ? "" : b;
    }

    public static String lastPathSegment(String path) {
        if (path == null)
            return "";
        int i = path.lastIndexOf('/');
        return (i >= 0 && i + 1 < path.length()) ? path.substring(i + 1) : path;
    }

    public static int parseYear(String s) {
        if (s == null)
            return 0;
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

    public static String readWikipediaLink(JsonNode node) {
        // direct "wikipedia" field
        String wiki = text(node, "wikipedia");
        if (!wiki.isBlank())
            return wiki;

        // links: [ { title, url }, ... ]
        JsonNode links = node.path("links");
        if (links.isArray()) {
            for (JsonNode l : links) {
                String url = l.path("url").asText("");
                if (url.contains("wikipedia.org"))
                    return url;
            }
        }
        return null;
    }

    public static String coverImage(int id, char size) {
        // b/id/{id}-{S|M|L}.jpg
        return "https://covers.openlibrary.org/b/id/" + id + "-" + size + ".jpg";
    }

    /* ============================== Author utils ============================== */

    public static LocalDate parseFlexibleDate(String s) {
        if (s == null || s.isBlank())
            return null;
        // Try ISO first
        List<String> patterns = List.of("yyyy-MM-dd", "yyyy-MM", "yyyy");
        for (String p : patterns) {
            try {
                if ("yyyy".equals(p))
                    return LocalDate.of(Integer.parseInt(s.substring(0, 4)), 1, 1);
                return LocalDate.parse(s, DateTimeFormatter.ofPattern(p));
            } catch (DateTimeParseException | NumberFormatException ignore) {
            }
        }
        // Last resort: extract a 4-digit year
        int y = parseYear(s);
        return y == 0 ? null : LocalDate.of(y, 1, 1);
    }

    public static String readBio(JsonNode author) {
        String bio = text(author, "bio");
        return bio == null ? "" : bio;
    }

    public static String authorImage(int id, char size) {
        // a/id/{id}-{S|M|L}.jpg
        return "https://covers.openlibrary.org/a/id/" + id + "-" + size + ".jpg";
    }
}
