package com.tgourouza.library_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultilingualList {
    private List<String> french;
    private List<String> spanish;
    private List<String> italian;
    private List<String> portuguese;
    private List<String> english;
    private List<String> german;
    private List<String> russian;
    private List<String> japanese;
}
