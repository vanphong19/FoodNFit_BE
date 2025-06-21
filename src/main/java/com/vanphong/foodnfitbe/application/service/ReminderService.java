package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;

import java.util.List;
import java.util.UUID;

public interface ReminderService {
    ReminderResponse createReminder(ReminderRequest request);
    List<ReminderResponse> getAllReminders();
    void createReminderForUser(UUID userId, ReminderRequest request);
    ReminderResponse getReminderById(Integer reminderId);
}
