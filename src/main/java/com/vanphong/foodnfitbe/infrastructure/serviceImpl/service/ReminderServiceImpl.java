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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<ReminderResponse> getAllReminders() {
        UUID userId = currentUser.getCurrentUserId();
        List<Reminders> reminders = reminderRepository.findAllByUserId(userId);
        return reminders.stream().map(reminderMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void createReminderForUser(UUID userId, ReminderRequest request) {
        Users users = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Reminders reminders = reminderMapper.toEntity(request);
        reminders.setUser(users);
        Reminders saved = reminderRepository.save(reminders);
        reminderMapper.toResponse(saved);
    }

    @Override
    public ReminderResponse getReminderById(Integer reminderId) {
        Optional<Reminders> reminders = reminderRepository.findById(reminderId);
        if(reminders.isEmpty()){
            throw new NotFoundException("Reminder not found");
        }
        return reminderMapper.toResponse(reminders.get());
    }
}
