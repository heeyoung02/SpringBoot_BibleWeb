package com.practice.bibletest.controller;

import com.practice.bibletest.dto.BibleDTO;
import com.practice.bibletest.dto.PageRequestDTO;
import com.practice.bibletest.service.BibleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class BibleController {
    private final BibleService bibleService;

    @GetMapping("/")
    public String home(Model model) {
        List<BibleDTO> bookDTOs = bibleService.getBookDTOs();
        model.addAttribute("bookDTOs", bookDTOs);
        return "bible/home";
    }
    @GetMapping("/list")
    public String list(@RequestParam("book") int book, @RequestParam(value = "chapter", defaultValue = "1") int chapter, Model model) {
        BibleDTO chaptersDTO = bibleService.getChaptersDTO(book);
        model.addAttribute("chaptersDTO", chaptersDTO);
        List<BibleDTO> listDTOs = bibleService.getContents(book, chapter);
        model.addAttribute("listDTOs", listDTOs);
        return "bible/list";
    }
    @GetMapping("/search")
    public String search() {
        return "bible/search";
    }

    @GetMapping("/searchResult")
    public String testResult(PageRequestDTO pageRequestDTO, Model model) {
        log.info("requestDTO is...." + pageRequestDTO);
        model.addAttribute("result", bibleService.getSearchPage(pageRequestDTO));
        return "bible/searchResult";
    }
}
