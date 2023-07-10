package com.practice.bibletest.controller;

import com.practice.bibletest.dto.BibleDTO;
import com.practice.bibletest.dto.SearchDTO;
import com.practice.bibletest.service.BibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bible")
public class BibleController {
    private final BibleService bibleService;
    @GetMapping("/test")
    public void test() {
        System.out.println("test mapping...........");
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<BibleDTO> bookDTOs = bibleService.getBookDTOs();
        model.addAttribute("bookDTOs", bookDTOs);
        return "bible/home";
    }
    @PostMapping("/home")
    public String search(SearchDTO searchDTO, RedirectAttributes redirectAttributes) {
        List<BibleDTO> bibleList = bibleService.getSearch(searchDTO);
        redirectAttributes.addFlashAttribute("bibleList", bibleList);
        redirectAttributes.addFlashAttribute("inputKeyword", searchDTO.getKeyword());
        return "redirect:search";
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
    public String searchResult(Model model) {
        @SuppressWarnings("unchecked")
        List<BibleDTO> bibleList = (List<BibleDTO>) model.getAttribute("bibleList");
        String inputKeyword = (String) model.getAttribute("inputKeyword");
        model.addAttribute("bibleList", bibleList);
        model.addAttribute("inputKeyword", inputKeyword);
        return "bible/search";
    }
    @PostMapping("/search")
    public String postSearch(SearchDTO searchDTO, RedirectAttributes redirectAttributes) {
        List<BibleDTO> bibleList = bibleService.getSearch(searchDTO);
        redirectAttributes.addFlashAttribute("bibleList", bibleList);
        redirectAttributes.addFlashAttribute("inputKeyword", searchDTO.getKeyword());
        return "redirect:search";
    }
}
