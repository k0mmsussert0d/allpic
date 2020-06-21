package com.ewsie.allpic.user.session;

import com.ewsie.allpic.user.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CleanupJob {

    private final SessionService sessionService;

    @Scheduled(cron = "* 0/30 * * * *")
    public void removeOldSessions() {
        sessionService.removeOlderThan(LocalDateTime.now());
    }
}

