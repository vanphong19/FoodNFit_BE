package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.domain.repository.ReminderRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.ReminderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {
    private final ReminderJpaRepository reminderJpaRepository;
    private final FcmService fcmService;

    @Scheduled(cron = "0 0 7 * * *")  // Mỗi ngày vào lúc 7:00 sáng
    public void dailyReminder() {
        // Lấy thời gian hiện tại và làm tròn đến giây
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        // Kiểm tra xem nhắc nhở cho ngày hôm nay đã tồn tại chưa
        Reminders existingReminder = reminderJpaRepository.findByScheduledTime(now.toLocalDate().atTime(7, 0));

        if (existingReminder == null) {
            // Nếu không có nhắc nhở, tạo một nhắc nhở mới
            Reminders reminder = new Reminders();
            reminder.setMessage("Chào buổi sáng! Hãy bắt đầu ngày mới vui vẻ và ghi lại thông tin bữa ăn của mình.");
            reminder.setScheduledTime(LocalDateTime.of(now.toLocalDate(), LocalTime.of(7, 0))); // Lên lịch 7h sáng
            reminder.setFrequency("daily");
            reminder.setReminderType("Ngày mới vui vẻ");
            reminder.setIsActive(false);  // Chưa gửi thông báo
            // Lưu nhắc nhở vào cơ sở dữ liệu
            reminderJpaRepository.save(reminder);

            // Gửi thông báo
            fcmService.send(reminder); // Gửi thông báo qua FCM
            reminder.setIsActive(true);  // Đánh dấu nhắc nhở là đã gửi
            reminderJpaRepository.save(reminder);  // Cập nhật trạng thái nhắc nhở
        } else {
            // Nếu nhắc nhở đã tồn tại, gửi lại thông báo và cập nhật thời gian nhắc nhở cho ngày hôm sau
            fcmService.send(existingReminder);  // Gửi lại thông báo qua FCM
            existingReminder.setScheduledTime(existingReminder.getScheduledTime().plusDays(1));  // Cập nhật lại thời gian nhắc nhở cho ngày hôm sau
            reminderJpaRepository.save(existingReminder);  // Cập nhật trạng thái nhắc nhở
        }
    }

    @Scheduled(cron = "0 0 7 * * MON")  // Mỗi thứ Hai vào lúc 7:00 sáng
    public void weeklyReminder() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        List<Reminders> list = reminderJpaRepository.findDueReminders(now, "weekly");

        for (Reminders reminder : list) {
            fcmService.send(reminder);
        }
    }

    @Scheduled(cron = "0 0 7 1 * *")  // Mỗi tháng vào ngày 1 lúc 7:00 sáng
    public void monthlyReminder() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        List<Reminders> list = reminderJpaRepository.findDueReminders(now, "monthly");

        for (Reminders reminder : list) {
            fcmService.send(reminder);
        }
    }

    @Scheduled(cron = "0 * * * * *")  // Mỗi phút một lần
    public void customReminderSchedule() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        List<Reminders> list = reminderJpaRepository.findDueReminders(now, "custom");

        // Duyệt qua các nhắc nhở cần gửi
        for (Reminders reminder : list) {
            fcmService.send(reminder);  // Gửi thông báo
            reminder.setIsActive(true);  // Đánh dấu nhắc nhở đã được gửi
            reminderJpaRepository.save(reminder);  // Lưu trạng thái nhắc nhở
        }
    }
}
