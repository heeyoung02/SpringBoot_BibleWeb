package com.practice.bibletest;

import com.practice.bibletest.dto.BibleDTO;
import com.practice.bibletest.dto.SearchDTO;
import com.practice.bibletest.service.BibleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BibleWebApplicationTests {
    @Autowired
    private BibleService service;
    @Test
    public void searchTest() {
        String keyword = " ";
        SearchDTO searchDTO = SearchDTO.builder()
                .type("content")
                .keyword(keyword)
                .build();
        List<BibleDTO> dto = service.getSearch(searchDTO);
        for(BibleDTO result : dto) {
            System.out.println(result);
        }
    }
    @Test
    public void test() {
        String keyword = "  ";
        SearchDTO searchDTO = SearchDTO.builder()
                .type("content")
                .keyword(keyword)
                .build();
        System.out.println(searchDTO.getKeywords());
    }
}
