package com.practice.bibletest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class SearchDTO {
    private String type; // 검색 조건 (book-chapter-verse 조합 / content 단어 조합)
    private String keyword;
    private List<String> keywords;

    public List<String> getKeywords() {
        if (keywords == null) {
            String trimmedKeyword = keyword.trim();
            if (!trimmedKeyword.isEmpty()) {
                keywords = Arrays.asList(trimmedKeyword.split("\\s+"));
            } else {
                keywords = Collections.emptyList(); // keyword가 비어있거나 공백만 있는 경우 빈 리스트 반환
            }
        }
        return keywords;
    }
}
