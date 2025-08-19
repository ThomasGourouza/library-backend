package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.constant.Genre;
import com.tgourouza.library_backend.constant.Subject;

public record Category(Audience audience, Genre genre, Subject subject) {

}
