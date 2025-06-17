package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.AnswerFeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AnswerFeedbackResponse;

public interface AnswerFeedbackService {
    AnswerFeedbackResponse createAnswerFeedback(AnswerFeedbackRequest answerFeedbackRequest);
}
