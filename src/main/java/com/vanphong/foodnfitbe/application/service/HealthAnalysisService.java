package com.vanphong.foodnfitbe.application.service;

import java.util.UUID;

public interface HealthAnalysisService {
    void analyzeAndCreateReminder(UUID userId);
}
