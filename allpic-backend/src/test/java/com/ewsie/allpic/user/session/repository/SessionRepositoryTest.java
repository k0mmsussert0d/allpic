package com.ewsie.allpic.user.session.repository;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.session.model.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class SessionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void whenFindBySessionIdentifier_thenReturnSession() {
        // given
        Session session = getSampleSession("identifier");
        User user = getSampleUser("foo");
        session.setUser(user);

        entityManager.persist(user);
        entityManager.persist(session);
        entityManager.flush();

        // when
        Optional<Session> found = sessionRepository.findBySessionIdentifier("identifier");

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getSessionIdentifier())
                .isEqualTo(session.getSessionIdentifier());
    }

    @Test
    public void whenRemoveByValidUntilBefore_thenReturnRemovedSessions() {
        // given
        //   these should be deleted
        Session session1 = getSampleSession("identifier1");
        User user1 = getSampleUser("foo1");
        session1.setUser(user1);

        Session session2 = getSampleSession("identifier2");
        User user2 = getSampleUser("foo2");
        session2.setUser(user2);

        //   this should be retained
        Session session3 = getSampleSession("identifier3");
        session3.setValidUntil(LocalDateTime.now().plusHours(2));
        User user3 = getSampleUser("foo3");
        session3.setUser(user3);

        entityManager.persist(user1);
        entityManager.persist(session1);
        entityManager.persist(user2);
        entityManager.persist(session2);
        entityManager.persist(user3);
        entityManager.persist(session3);
        entityManager.flush();

        // when
        List<Session> removed = sessionRepository.removeByValidUntilBefore(LocalDateTime.now());

        // then
        assertThat(removed.contains(session1)).isTrue();
        assertThat(removed.contains(session2)).isTrue();
        assertThat(removed.contains(session3)).isFalse();

        List<Session> retained = sessionRepository.findAll();
        assertThat(retained.size()).isEqualTo(1);
        assertThat(retained.contains(session3)).isTrue();
    }

    private Session getSampleSession(String identifier) {
        Session session = new Session();
        session.setSessionIdentifier(identifier);
        session.setValidUntil(LocalDateTime.now().minusHours(2));
        return session;
    }

    private User getSampleUser(String username) {
        User foo = new User();
        foo.setUsername(username);

        //   filling mandatory NOT NULL fields
        foo.setPassword("password");
        foo.setEmail(username + "@example.com");
        foo.setRegisterTime(LocalDateTime.now());
        foo.setIsActive(true);
        return foo;
    }
}
