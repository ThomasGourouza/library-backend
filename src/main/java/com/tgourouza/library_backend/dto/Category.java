package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.constant.Type;

public record Category(Audience audience, Type type, com.tgourouza.library_backend.constant.Category category) {

}
