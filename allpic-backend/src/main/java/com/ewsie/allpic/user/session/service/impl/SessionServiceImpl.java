package com.ewsie.allpic.user.session.service.impl;

import com.ewsie.allpic.user.session.model.Session;
import com.ewsie.allpic.user.session.repository.SessionRepository;
import com.ewsie.allpic.user.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Session findByIdentifier(String identifier) {
        return sessionRepository.findBySessionIdentifier(identifier);
    }

    @Override
    public void remove(Session session) {
        sessionRepository.delete(session);
    }

    @Override
    public void removeByIdentifier(String identifier) {
        Optional<Session> session = Optional.ofNullable(findByIdentifier(identifier));

        if (session.isPresent()) {
            remove(session.get());
        }
    }
}
