package com.practice.bibletest;

import com.practice.bibletest.dto.BibleDTO;
import com.practice.bibletest.dto.PageRequestDTO;
import com.practice.bibletest.dto.PageResultDTO;
import com.practice.bibletest.entity.BibleKorhrv;
import com.practice.bibletest.service.BibleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BibleWebApplicationTests {
    @Autowired
    private BibleService service;
    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(50).build();
        PageResultDTO<BibleDTO, BibleKorhrv> resultDTO = service.getSearchPage(pageRequestDTO);
        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());
        System.out.println("-----------------------");
        for (BibleDTO bibleDTO : resultDTO.getDtoList()) {
            System.out.println(bibleDTO);
        }
        System.out.println("-----------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(50)
                .type("content")
                .keyword("예수 ") // 검색 키워드
                .build();

        PageResultDTO<BibleDTO, BibleKorhrv> resultDTO = service.getSearchPage(pageRequestDTO);
        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL PAGE: " + resultDTO.getTotalPage());
        System.out.println("TOTAL DATA: " + resultDTO.getTotalData());
        System.out.println("-------------------------------");
        for (BibleDTO bibleDTO : resultDTO.getDtoList()) {
            System.out.println(bibleDTO);
        }
        System.out.println("================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
