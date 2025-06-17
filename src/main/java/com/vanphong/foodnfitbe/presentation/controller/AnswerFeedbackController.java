package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.AnswerFeedbackService;
import com.vanphong.foodnfitbe.domain.entity.AnswerFeedback;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.AnswerFeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AnswerFeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer-feedback")
@RequiredArgsConstructor
public class AnswerFeedbackController {
    private final AnswerFeedbackService answerFeedbackService;
    @PostMapping("/create")
    public ResponseEntity<AnswerFeedbackResponse> create(@RequestBody AnswerFeedbackRequest request) {
        AnswerFeedbackResponse response = answerFeedbackService.createAnswerFeedback(request);
        return ResponseEntity.ok(response);
    }
}
