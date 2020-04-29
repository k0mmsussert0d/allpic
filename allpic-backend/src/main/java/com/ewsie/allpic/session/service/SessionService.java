package com.ewsie.allpic.session.service;

import com.ewsie.allpic.session.model.Session;

public interface SessionService {

    void create(Session session);

    Session findById(Long id);
}
