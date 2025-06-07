package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;

public interface ReminderService {
    ReminderResponse createReminder(ReminderRequest request);
}
