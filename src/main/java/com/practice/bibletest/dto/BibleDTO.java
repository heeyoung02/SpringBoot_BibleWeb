package com.practice.bibletest.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BibleDTO {
    private int id;
    private int book;
    private int chapter;
    private int verse;
    private String content;
    private String bookName;
    private List<Integer> chapterList;
    private int chapterCount;

}
