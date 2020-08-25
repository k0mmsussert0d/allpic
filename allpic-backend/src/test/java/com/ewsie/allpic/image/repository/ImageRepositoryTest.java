package com.ewsie.allpic.image.repository;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void whenFindById_thenReturnImage() {
        // given
        Image image = getSampleImage("token");
        Image saved = entityManager.persist(image);
        entityManager.flush();

        // when
        Optional<Image> found = imageRepository.findById(saved.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    public void whenFindByToken_thenReturnImage() {
        // given
        Image image = getSampleImage("token");
        entityManager.persist(image);
        entityManager.flush();

        // when
        Optional<Image> found = imageRepository.findByToken(image.getToken());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getToken()).isEqualTo(image.getToken());
    }

    @Test
    public void whenFindAllByUploader_thenReturnImages() {
        // given
        Image image = getSampleImage("token");
        User user = getSampleUser();
        image.setUploader(user);
        entityManager.persist(user);
        entityManager.persist(image);
        entityManager.flush();

        // when
        List<Image> found = imageRepository.findAllByUploader(user);

        // then
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.contains(image)).isTrue();
    }

    @Test
    public void whenFindAllOrderByUploadedTimeDesc_thenReturnImages() {
        // given
        Image image1 = getSampleImage("token1");
        Image image2 = getSampleImage("token2");
    }

    private Image getSampleImage(String token) {
        Image image = new Image();
        image.setToken(token);
        image.setUploadTime(LocalDateTime.now());
        image.setIsPublic(true);
        image.setIsActive(true);
        return image;
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
