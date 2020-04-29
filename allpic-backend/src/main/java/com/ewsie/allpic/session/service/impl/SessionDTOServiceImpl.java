package com.ewsie.allpic.session.service.impl;

import com.ewsie.allpic.session.model.Session;
import com.ewsie.allpic.session.model.SessionDTO;
import com.ewsie.allpic.session.service.SessionDTOService;
import com.ewsie.allpic.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
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

        return session.isPresent() ? modelMapper.map(session, SessionDTO.class) : null;
    }
}
