package com.ewsie.allpic.user.session.repository;

import com.ewsie.allpic.user.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionIdentifier(String sessionIdentifier);

    List<Session> removeByValidUntilBefore(LocalDateTime time);
}
