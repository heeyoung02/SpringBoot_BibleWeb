package com.practice.bibletest.service;

import com.practice.bibletest.dto.BibleDTO;
import com.practice.bibletest.dto.SearchDTO;
import com.practice.bibletest.entity.BibleKorhrv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

@SpringBootTest
public class BibleServiceTests {
    @Autowired
    private BibleService bibleService;
    @Test
    public void getBookDTOsTest() {
        List<BibleDTO> result = bibleService.getBookDTOs();
        for (BibleDTO dto : result) {
            System.out.println(dto);
        }
    }
    @Test
    public void test1() {
        BibleDTO dto = bibleService.getChaptersDTO(1);
        System.out.println(dto);
    }
    @Test
    public void testSearch() {
        //SearchDTO searchDTO = SearchDTO.builder().type("content").keyword("하늘에 계신").build();
        //SearchDTO searchDTO = SearchDTO.builder().type("bcv").keyword("창세기 1장 18절").build();
        SearchDTO searchDTO = SearchDTO.builder().type("").keyword("").build();
        List<BibleDTO> result = bibleService.getSearch(searchDTO);
        for (BibleDTO dto : result) {
            System.out.println(dto.getBookName()+" "+dto.getChapter()+"장 "+dto.getVerse()+"절..." +dto.getContent());
        }
        System.out.println("검색 총 갯수는 : "+result.size());
    }
}
