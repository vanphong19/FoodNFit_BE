package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.TranslateService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/translate")
@RequiredArgsConstructor
public class TranslateController {
    private final TranslateService translateService;
    @GetMapping("/get")
    public String translateText(@RequestParam String text) {
        return translateService.translateToVietnamese(text);
    }
}
