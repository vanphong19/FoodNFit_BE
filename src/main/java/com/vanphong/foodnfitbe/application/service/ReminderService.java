package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;

import java.util.List;

public interface ReminderService {
    ReminderResponse createReminder(ReminderRequest request);
    List<ReminderResponse> getAllReminders();
}
