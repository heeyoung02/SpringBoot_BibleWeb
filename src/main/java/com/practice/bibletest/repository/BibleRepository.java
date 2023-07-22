package com.practice.bibletest.repository;

import com.practice.bibletest.entity.BibleKorhrv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BibleRepository extends JpaRepository<BibleKorhrv, Integer>,
        QuerydslPredicateExecutor<BibleKorhrv> {
    @Query("SELECT DISTINCT b.book FROM BibleKorhrv b")
    List<Object> findDistinctBooks(); // book 데이터 리스트 가져오기
    @Query("SELECT DISTINCT b.chapter FROM BibleKorhrv b WHERE b.book = :book")
    List<Integer> findDistinctChaptersByBook(@Param("book") int book); // book에 해당하는 chapter 리스트 가져오기
    List<BibleKorhrv> findByBookAndChapter(int book, int chapter); // book,chapter에 해당하는 데이터 리스트

}
