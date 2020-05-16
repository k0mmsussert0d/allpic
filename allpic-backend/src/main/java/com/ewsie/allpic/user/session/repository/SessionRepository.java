package com.ewsie.allpic.user.session.repository;

import com.ewsie.allpic.user.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findBySessionIdentifier(String sessionIdentifier);
}
