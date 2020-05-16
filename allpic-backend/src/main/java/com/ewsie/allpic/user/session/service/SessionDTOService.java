package com.ewsie.allpic.user.session.service;

import com.ewsie.allpic.user.session.model.SessionDTO;

public interface SessionDTOService {

    void create(SessionDTO sessionDTO);

    SessionDTO findById(Long id);

    SessionDTO findByIdentifier(String identifier);
}
