package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FeedbackService;
import com.vanphong.foodnfitbe.domain.entity.Feedback;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    @PostMapping("/create")
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest request) {
        FeedbackResponse feedbackResponse = feedbackService.createFeedback(request);
        return ResponseEntity.ok(feedbackResponse);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedback() {
        List<FeedbackResponse> feedbackResponse = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackResponse);
    }
}
