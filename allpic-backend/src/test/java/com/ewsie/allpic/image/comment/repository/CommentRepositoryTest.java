package com.ewsie.allpic.image.comment.repository;

import com.ewsie.allpic.image.comment.model.Comment;
import com.ewsie.allpic.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private  CommentRepository commentRepository;

    @Test
    public void whenFindById_thenReturnComment() {
        // given
        Comment comment = new Comment();
        User user = getSampleUser();
        comment.setAuthor(user);
        entityManager.persist(user);
        Comment saved = entityManager.persist(comment);
        entityManager.flush();

        // when
        Optional<Comment> found = commentRepository.findById(saved.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    private User getSampleUser() {
        User foo = new User();
        foo.setUsername("foo");

        //   filling mandatory NOT NULL fields
        foo.setPassword("password");
        foo.setEmail("email@example.com");
        foo.setRegisterTime(LocalDateTime.now());
        foo.setIsActive(true);
        return foo;
    }
}
