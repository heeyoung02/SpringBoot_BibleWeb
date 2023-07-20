package com.practice.bibletest.service;

import com.practice.bibletest.dto.BibleDTO;
import com.practice.bibletest.dto.SearchDTO;
import com.practice.bibletest.entity.BibleKorhrv;
import com.practice.bibletest.entity.QBibleKorhrv;
import com.practice.bibletest.repository.BibleRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BibleService {
    @Autowired
    private BibleRepository bibleRepository;

    public List<BibleDTO> getBookDTOs() { // 중복되지 않는 book과 bookName 리스트
        List<Object> bookList = bibleRepository.findDistinctBooks();
        List<BibleDTO> result = new ArrayList<>();
        for (Object bookObj : bookList) {
            BibleDTO bookDTO = BibleDTO.builder()
                    .book((int) bookObj)
                    .bookName(getBookName((int)bookObj))
                    .build();
            result.add(bookDTO);
        }
        return result;
    }
    public BibleDTO getChaptersDTO(int book) { // book에 해당하는 chapter리스트, chapter 갯수 나열
        List<Integer> chapterList = bibleRepository.findDistinctChaptersByBook(book);
        return BibleDTO.builder()
                .book(book)
                .bookName(getBookName(book))
                .chapterList(chapterList)
                .chapterCount(chapterList.size())
                .build();
    }
    public List<BibleDTO> getContents(int book, int chapter) { // book,chapter에 맞는 verse와 content 가져오기
        List<BibleDTO> result = new ArrayList<>();
        List<BibleKorhrv> bibleEntities = bibleRepository.findByBookAndChapter(book, chapter);
        for (BibleKorhrv bibleEntity : bibleEntities) {
            BibleDTO dto = BibleDTO.builder()
                    .verse(bibleEntity.getVerse())
                    .content(bibleEntity.getContent())
                    .build();
            result.add(dto);
        }
        return result;
    }

    // 검색관련
    public List<BibleDTO> getSearch(SearchDTO searchDTO) {
        String type = searchDTO.getType();
        List<String> keywords = searchDTO.getKeywords();
        List<BibleDTO> result = new ArrayList<>();
        if(keywords.isEmpty())
            return result;

        if(type.contains("content")) {
            List<BibleDTO> contentResult = searchContent(keywords);
            result = (contentResult != null) ? contentResult : result;
        }
        if(type.contains("bcv")) {
            BibleDTO bcvResult = searchBCV(keywords);
            if(bcvResult != null)
                result.add(bcvResult);
        }
        return result; // 잘못된 값이 입력되면 빈 List 반환
    }

    private List<BibleDTO> searchContent(List<String> keywords) {
        Pageable pageable = Pageable.unpaged();
        BooleanBuilder builder = new BooleanBuilder();
        QBibleKorhrv qBibleKorhrv = QBibleKorhrv.bibleKorhrv;

        for (String word : keywords) {
            BooleanExpression expression = qBibleKorhrv.content.contains(word);
            builder.and(expression);
        }
        Page<BibleKorhrv> result = bibleRepository.findAll(builder, pageable);
        return entityToDTOList(result.getContent());
    }

    private BibleDTO searchBCV(List<String> keywords) {

        if (keywords.size() != 3) return null;

        int book = getBookNumber(keywords.get(0));
        int chapter = extractNumber(keywords.get(1));
        int verse = extractNumber(keywords.get(2));

        if (book <= 0 || chapter <= 0 || verse <= 0) return null;

        BibleKorhrv resultData = bibleRepository.findByBookAndChapterAndVerse(book, chapter, verse);
        return resultData != null ? entityToDTO(resultData) : null;
    }

    private int extractNumber(String str) { // 숫자 추출 로직. 추출숫자가 없다면 -1 반환
        String numberString = str.replaceAll("\\D+", "");
        return numberString.isEmpty() ? -1 : Integer.parseInt(numberString);
    }
    private List<BibleDTO> entityToDTOList(List<BibleKorhrv> entities) { // entities->DTOs 변환
        return entities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    private BibleDTO entityToDTO(BibleKorhrv bible) { // entity->DTO 변환
        return BibleDTO.builder()
                .id(bible.getId())
                .book(bible.getBook())
                .bookName(getBookName(bible.getBook()))
                .chapter(bible.getChapter())
                .verse(bible.getVerse())
                .content(bible.getContent())
                .build();
    }

    // book을 받아 성경 목록의 이름 전달
    private String getBookName(int book) {
        switch (book) {
            case 1:
                return "창세기";
            case 2:
                return "출애굽기";
            case 3:
                return "레위기";
            case 4:
                return "민수기";
            case 5:
                return "신명기";
            case 6:
                return "여호수아";
            case 7:
                return "사사기";
            case 8:
                return "룻기";
            case 9:
                return "사무엘상";
            case 10:
                return "사무엘하";
            case 11:
                return "열왕기상";
            case 12:
                return "열왕기하";
            case 13:
                return "역대상";
            case 14:
                return "역대하";
            case 15:
                return "에스라";
            case 16:
                return "느헤미야";
            case 17:
                return "에스더";
            case 18:
                return "욥기";
            case 19:
                return "시편";
            case 20:
                return "잠언";
            case 21:
                return "전도서";
            case 22:
                return "아가";
            case 23:
                return "이사야";
            case 24:
                return "예레미야";
            case 25:
                return "예레미야애가";
            case 26:
                return "에스겔";
            case 27:
                return "다니엘";
            case 28:
                return "호세아";
            case 29:
                return "요엘";
            case 30:
                return "아모스";
            case 31:
                return "오바댜";
            case 32:
                return "요나";
            case 33:
                return "미가";
            case 34:
                return "나훔";
            case 35:
                return "하박국";
            case 36:
                return "스바냐";
            case 37:
                return "학개";
            case 38:
                return "스가랴";
            case 39:
                return "말라기";
            case 40:
                return "마태복음";
            case 41:
                return "마가복음";
            case 42:
                return "누가복음";
            case 43:
                return "요한복음";
            case 44:
                return "사도행전";
            case 45:
                return "로마서";
            case 46:
                return "고린도전서";
            case 47:
                return "고린도후서";
            case 48:
                return "갈라디아서";
            case 49:
                return "에베소서";
            case 50:
                return "빌립보서";
            case 51:
                return "골로새서";
            case 52:
                return "데살로니가전서";
            case 53:
                return "데살로니가후서";
            case 54:
                return "디모데전서";
            case 55:
                return "디모데후서";
            case 56:
                return "디도서";
            case 57:
                return "빌레몬서";
            case 58:
                return "히브리서";
            case 59:
                return "야고보서";
            case 60:
                return "베드로전서";
            case 61:
                return "베드로후서";
            case 62:
                return "요한1서";
            case 63:
                return "요한2서";
            case 64:
                return "요한3서";
            case 65:
                return "유다서";
            case 66:
                return "요한계시록";
            default:
                return null;
        }
    }

    // 검색시 성경 목록이름을 받아 book 전달
    private int getBookNumber(String bookName) {
        switch (bookName) {
            case "창세기":
                return 1;
            case "출애굽기":
                return 2;
            case "레위기":
                return 3;
            case "민수기":
                return 4;
            case "신명기":
                return 5;
            case "여호수아":
                return 6;
            case "사사기":
                return 7;
            case "룻기":
                return 8;
            case "사무엘상":
                return 9;
            case "사무엘하":
                return 10;
            case "열왕기상":
                return 11;
            case "열왕기하":
                return 12;
            case "역대상":
                return 13;
            case "역대하":
                return 14;
            case "에스라":
                return 15;
            case "느헤미야":
                return 16;
            case "에스더":
                return 17;
            case "욥기":
                return 18;
            case "시편":
                return 19;
            case "잠언":
                return 20;
            case "전도서":
                return 21;
            case "아가":
                return 22;
            case "이사야":
                return 23;
            case "예레미야":
                return 24;
            case "예레미야애가":
                return 25;
            case "에스겔":
                return 26;
            case "다니엘":
                return 27;
            case "호세아":
                return 28;
            case "요엘":
                return 29;
            case "아모스":
                return 30;
            case "오바댜":
                return 31;
            case "요나":
                return 32;
            case "미가":
                return 33;
            case "나훔":
                return 34;
            case "하박국":
                return 35;
            case "스바냐":
                return 36;
            case "학개":
                return 37;
            case "스가랴":
                return 38;
            case "말라기":
                return 39;
            case "마태복음":
                return 40;
            case "마가복음":
                return 41;
            case "누가복음":
                return 42;
            case "요한복음":
                return 43;
            case "사도행전":
                return 44;
            case "로마서":
                return 45;
            case "고린도전서":
                return 46;
            case "고린도후서":
                return 47;
            case "갈라디아서":
                return 48;
            case "에베소서":
                return 49;
            case "빌립보서":
                return 50;
            case "골로새서":
                return 51;
            case "데살로니가전서":
                return 52;
            case "데살로니가후서":
                return 53;
            case "디모데전서":
                return 54;
            case "디모데후서":
                return 55;
            case "디도서":
                return 56;
            case "빌레몬서":
                return 57;
            case "히브리서":
                return 58;
            case "야고보서":
                return 59;
            case "베드로전서":
                return 60;
            case "베드로후서":
                return 61;
            case "요한1서":
                return 62;
            case "요한2서":
                return 63;
            case "요한3서":
                return 64;
            case "유다서":
                return 65;
            case "요한계시록":
                return 66;
            default:
                return 0;
        }
    }

}
