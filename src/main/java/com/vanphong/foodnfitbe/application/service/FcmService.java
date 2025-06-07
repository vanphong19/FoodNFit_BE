package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.Reminders;

public interface FcmService {
    void send(Reminders reminder);
}
