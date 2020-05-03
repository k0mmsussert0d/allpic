package com.ewsie.allpic.user.session;

import com.ewsie.allpic.user.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class CleanupJob {

    SessionService sessionService;

    @Scheduled(cron = "0 0 5 * * *")
    public void removeOldSessions() {

    }
}

