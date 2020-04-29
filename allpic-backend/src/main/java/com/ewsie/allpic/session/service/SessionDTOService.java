package com.ewsie.allpic.session.service;

import com.ewsie.allpic.session.model.SessionDTO;

public interface SessionDTOService {

    void create(SessionDTO sessionDTO);

    SessionDTO findById(Long id);
}
