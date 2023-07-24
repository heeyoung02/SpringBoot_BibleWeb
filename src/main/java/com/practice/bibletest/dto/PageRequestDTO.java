package com.practice.bibletest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 100;
    }
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }

    private List<String> keywords;
    public List<String> getKeywords() {
        if (keyword.trim() != "" && keywords == null) { // keywords가 빈값이고 keyword의 공백을 제거한 값이 빈값이 아닐때
            String trimmedKeyword = keyword.trim();
            if (!trimmedKeyword.isEmpty() && trimmedKeyword != "") {
                keywords = Arrays.asList(trimmedKeyword.split("\\s+"));
            } else {
                keywords = Collections.emptyList(); // keyword가 비어있거나 공백만 있는 경우 빈 리스트 반환
            }
        }
        return keywords; // 조건문이 실행되지 않았을대 null값으로 입력
    }

}
