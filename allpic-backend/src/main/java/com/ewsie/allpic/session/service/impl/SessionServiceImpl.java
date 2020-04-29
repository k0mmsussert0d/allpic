package com.ewsie.allpic.session.service.impl;

import com.ewsie.allpic.session.model.Session;
import com.ewsie.allpic.session.repository.SessionRepository;
import com.ewsie.allpic.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public void create(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public Session findById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }
}
