package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.ReminderService;
import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reminder")
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;
    @PostMapping("/create")
    public ResponseEntity<ReminderResponse> create(@RequestBody ReminderRequest request) {
        ReminderResponse response = reminderService.createReminder(request);
        return ResponseEntity.ok(response);
    }
}
