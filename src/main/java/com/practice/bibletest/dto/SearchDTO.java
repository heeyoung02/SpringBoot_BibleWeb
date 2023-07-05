package com.practice.bibletest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SearchDTO {
    private String type; // 검색 조건 (book-chapter-verse 조합 / content 단어 조합)
    private String keyword;
}
