package com.tgourouza.library_backend.dto.constant;

import com.tgourouza.library_backend.constant.LiteraryGenre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiteraryGenreDTO {
    private Long id;
    private LiteraryGenre name;
}
