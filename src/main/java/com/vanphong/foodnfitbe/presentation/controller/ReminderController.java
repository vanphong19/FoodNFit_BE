package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.ReminderService;
import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getAll")
    public ResponseEntity<List<ReminderResponse>> getAll() {
        List<ReminderResponse> reminders = reminderService.getAllReminders();
        return ResponseEntity.ok(reminders);
    }
    @GetMapping("getById/{id}")
    public ResponseEntity<ReminderResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(reminderService.getReminderById(id));
    }
}
