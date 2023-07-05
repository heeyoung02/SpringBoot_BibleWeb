package com.practice.bibletest.repository;

import com.practice.bibletest.entity.BibleKorhrv;
import com.practice.bibletest.entity.QBibleKorhrv;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class BibleRepositoryTests {
    @Autowired
    BibleRepository bibleRepository;
    @Test
    public void readTest() {
        System.out.println("test!!!");
        Optional<BibleKorhrv> result = bibleRepository.findById(20123);
        BibleKorhrv bible = result.get();
        System.out.println(bible);
    }
    @Test
    public void testfindDistinctBooks() {
        List<Object> result = bibleRepository.findDistinctBooks();
        for (Object obj : result) {
            System.out.println(obj);
        }
    }
    @Test
    public void test1() {
        List<Integer> chapters = bibleRepository.findDistinctChaptersByBook(1);
        System.out.println(chapters);
    }
    @Test
    public void test2() {
        List<BibleKorhrv> bibleList = bibleRepository.findByBookAndChapter(1,1);
        for (BibleKorhrv bible : bibleList) {
            System.out.println(bible);
        }
    }
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        QBibleKorhrv qBibleKorhrv = QBibleKorhrv.bibleKorhrv;
        String keyword = "하나님";
        String keyword1 = "말씀";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qBibleKorhrv.content.contains(keyword1);
        BooleanExpression expression1 = qBibleKorhrv.content.contains(keyword);
        builder.and(expression).and(expression1);
        Page<BibleKorhrv> result = bibleRepository.findAll(builder, pageable);
        result.stream().forEach(bibleKorhrv -> {
            System.out.println(bibleKorhrv);
        });
    }
    @Test
    public void testQuery2() {
        String keyword = "하나님 아버지 요셉";
        if (keyword.trim().isEmpty()) {
            return;
        }
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").ascending());
        BooleanBuilder builder = new BooleanBuilder();
        QBibleKorhrv qBibleKorhrv = QBibleKorhrv.bibleKorhrv;
        //BooleanExpression expression = qBibleKorhrv.content.contains(keyword);
        List<String> keywords = Arrays.stream(keyword.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        for (String word : keywords) {
            BooleanExpression expression = qBibleKorhrv.content.contains(word);
            builder.and(expression);
        }
        Page<BibleKorhrv> result = bibleRepository.findAll(builder, pageable);
        result.stream().forEach(bibleKorhrv -> {
            System.out.println(bibleKorhrv);
        });
    }
    @Test
    public void testBCV() {
        BibleKorhrv result = bibleRepository.findByBookAndChapterAndVerse(1,1,1);
        System.out.println(result);
    }
}
