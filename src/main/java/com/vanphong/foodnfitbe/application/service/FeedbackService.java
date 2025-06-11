package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.FeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse createFeedback(FeedbackRequest feedbackRequest);
    List<FeedbackResponse> getAllFeedback();
    Long countAllFeedback();
}
