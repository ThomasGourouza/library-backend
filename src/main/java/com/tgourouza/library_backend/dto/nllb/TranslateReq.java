package com.tgourouza.library_backend.dto.nllb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TranslateReq(
    String text,
    @JsonProperty("source") String sourceLang,
    @JsonProperty("target") String targetLang
) {}
