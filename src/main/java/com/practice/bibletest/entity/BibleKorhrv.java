package com.practice.bibletest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BibleKorhrv {
    @Id
    private int id;
    private int book; // 성경 목록 (1~66)
    private int chapter; // 장
    private int verse; // 절
    private String content; // 본문

}
