package com.tgourouza.library_backend.dto;

import java.util.Set;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.constant.Form;
import com.tgourouza.library_backend.constant.Genre;
import com.tgourouza.library_backend.constant.Subject;

public record Category(
        Set<Audience> audiences,
        Set<Genre> genres,
        Set<Subject> subjects,
        Set<Form> forms) {
}
