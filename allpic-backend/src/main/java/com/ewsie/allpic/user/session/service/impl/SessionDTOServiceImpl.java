package com.ewsie.allpic.user.session.service.impl;

import com.ewsie.allpic.user.session.model.Session;
import com.ewsie.allpic.user.session.model.SessionDTO;
import com.ewsie.allpic.user.session.service.SessionDTOService;
import com.ewsie.allpic.user.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class SessionDTOServiceImpl implements SessionDTOService {

    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @Override
    public void create(SessionDTO sessionDTO) {
        sessionService.create(modelMapper.map(sessionDTO, Session.class));
    }

    @Override
    public SessionDTO findById(Long id) {
        Optional<Session> session = Optional.ofNullable(sessionService.findById(id));

        return session.map(value -> modelMapper.map(value, SessionDTO.class)).orElse(null);
    }

    @Override
    public SessionDTO findByIdentifier(String identifier) {
        Optional<Session> session = Optional.ofNullable(sessionService.findByIdentifier(identifier));

        return session.map(value -> modelMapper.map(value, SessionDTO.class)).orElse(null);
    }
}
