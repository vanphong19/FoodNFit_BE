package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.ReminderService;
import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.ReminderRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.ReminderMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {
    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    @Override
    public ReminderResponse createReminder(ReminderRequest request) {
        UUID userId = currentUser.getCurrentUserId();
        Users users = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Reminders reminders = reminderMapper.toEntity(request);
        reminders.setUser(users);
        Reminders saved = reminderRepository.save(reminders);
        return reminderMapper.toResponse(saved);
    }
}
