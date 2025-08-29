package com.tgourouza.library_backend.service;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.nllb.TranslateResponse;
import com.tgourouza.library_backend.mapper.NllbLangMapper;

@Service
public class NllbService {
    private final RestClient nllbClient;
    private final NllbLangMapper nllbLangMapper;
    private final LanguageDetector detector;
    // TODO: 100, 150 or 200 ?
    private final int DEFAULT_MAX_CHARS = 200;

    public NllbService(@Qualifier("nllbClient") RestClient nllbClient, LanguageDetector detector,
            NllbLangMapper nllbLangMapper) {
        this.nllbClient = nllbClient;
        this.detector = detector;
        this.nllbLangMapper = nllbLangMapper;
    }

    public Multilingual translateText(String text, Language sourceLanguage) {
        Language source = sourceLanguage != null ? sourceLanguage : detector.detectLanguageOf(text);
        return new Multilingual(
                translate(text, source, Language.FRENCH),
                translate(text, source, Language.SPANISH),
                translate(text, source, Language.ITALIAN),
                translate(text, source, Language.PORTUGUESE),
                translate(text, source, Language.ENGLISH),
                translate(text, source, Language.GERMAN),
                translate(text, source, Language.RUSSIAN),
                translate(text, source, Language.JAPANESE));
    }

    private String translate(String text, Language source, Language target) {
        if (text == null || text.isBlank() || source == Language.UNKNOWN) {
            return text;
        }
        String src = nllbLangMapper.toNllb(source)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported source language: " + source));
        String tgt = nllbLangMapper.toNllb(target)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported target language: " + target));
        if (src.equals(tgt)) {
            return text;
        }
        TranslateResponse resp = nllbTranslation(text, src, tgt);
        if (resp == null || resp.translation() == null)
            throw new IllegalStateException("Empty translation from NLLB server");
        return resp.translation();
    }

    // TODO: not always english: remove or update
    public String translateToEnglish(String text) {
        if (text == null || text.isBlank())
            return text;
        Language source = detector.detectLanguageOf(text);
        Language target = Language.ENGLISH;
        return translate(text, source, target);
    }

    public String translateLargeText(String text, Language target) {
        if (text == null || text.isBlank())
            return text;
        Language source = detector.detectLanguageOf(text);

        List<String> chunks = chunkBySentences(text, DEFAULT_MAX_CHARS);
        StringBuilder out = new StringBuilder(text.length());

        for (String chunk : chunks) {
            String resp = translate(chunk, source, target);
            out.append(resp);
        }
        // Only add a space after "." if there isn’t one already (and avoid trailing space at end of string)
        return out.toString().replaceAll("\\.(?!\\s|\\)|$)", ". ");
    }

    private TranslateResponse nllbTranslation(String text, String sourceLanguage, String targetLanguage) {
        return nllbClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("text", text, "source", sourceLanguage, "target", targetLanguage))
                .retrieve()
                .body(TranslateResponse.class);
    }

    private List<String> chunkBySentences(String text, int maxChars) {
        List<String> result = new ArrayList<>();
        BreakIterator it = BreakIterator.getSentenceInstance(Locale.ROOT);
        it.setText(text);

        StringBuilder buf = new StringBuilder(Math.min(text.length(), maxChars));
        int start = it.first();
        for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
            String sentence = text.substring(start, end);

            // If a single sentence is huge, hard-split it
            if (sentence.length() > maxChars) {
                if (buf.length() > 0) {
                    result.add(buf.toString());
                    buf.setLength(0);
                }
                for (int i = 0; i < sentence.length(); i += maxChars) {
                    int j = Math.min(i + maxChars, sentence.length());
                    result.add(sentence.substring(i, j));
                }
                continue;
            }

            // Normal case: append to buffer or flush
            if (buf.length() + sentence.length() > maxChars) {
                result.add(buf.toString());
                buf.setLength(0);
            }
            buf.append(sentence);
        }
        if (buf.length() > 0)
            result.add(buf.toString());

        return result;
    }
}
