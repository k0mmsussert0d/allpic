package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.image.repository.ImageRepository;
import com.ewsie.allpic.image.service.ImageService;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ImageServiceImplTest {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserService userService;

    ImageService imageService;

    @BeforeEach
    public void setUp() {
        imageService = new ImageServiceImpl(imageRepository);
    }

    @Test
    public void whenFindById_thenReturnImage() {
        // given
        Image image = getSampleImage("ABCDE");
        Image savedImage = imageRepository.save(image);

        // when
        Image found = imageService.findById(savedImage.getId());

        // then
        assertThat(found).isEqualTo(savedImage);
    }

    @Test
    public void whenFindByToken() {
        // given
        Image image = getSampleImage("ABCDE");
        imageRepository.save(image);

        // when
        Image found = imageService.findByToken(image.getToken());

        // then
        assertThat(found).isEqualToIgnoringNullFields(image);
    }

    @Test
    public void whenFindAllUploadedBy_thenReturnImages() {
        // given
        Image image1 = getSampleImage("TOKEN1");
        Image image2 = getSampleImage("TOKEN2");
        User user = getSampleUser("username");
        image1.setUploader(user);
        image2.setUploader(user);
        userService.create(user);
        List<Image> toBeSaved = List.of(image1, image2);
        imageRepository.saveAll(toBeSaved);

        // when
        List<Image> found = imageService.findAllUploadedBy(user);

        // then
        assertThat(found)
                .as("All images uploaded by the user should be returned")
                .hasSize(2)
                .usingElementComparatorIgnoringFields("id")
                .containsAll(toBeSaved);
    }

    @Test
    public void whenFindAllOrderByUploadedTimeDesc_thenReturnImages() {
        // given
        Image image1 = getSampleImage("TOKEN1");
        Image image2 = getSampleImage("TOKEN2");
        Image image3 = getSampleImage("TOKEN3");
        image1.setUploadTime(LocalDateTime.now());
        image2.setUploadTime(LocalDateTime.now().minusHours(1));
        image3.setUploadTime(LocalDateTime.now().minusHours(2));
        List<Image> toBeSaved = List.of(image1, image2, image3);
        imageRepository.saveAll(toBeSaved);

        // when
        List<Image> found = imageService.findAllOrderByUploadedTimeDesc();

        // then
        assertThat(found)
                .as("Should return all images sorted by upload time descending")
                .hasSize(3)
                .usingElementComparatorIgnoringFields("id")
                .containsExactlyElementsOf(toBeSaved);

    }

    @Test
    public void whenSave_thenReturnImage() {
        // given
        Image image = getSampleImage("TOKEN");

        // when
        Image savedImage = imageService.save(image);

        // then
        Optional<Image> retrievedImage = imageRepository.findById(savedImage.getId());

        assertThat(retrievedImage.isPresent()).isTrue();
        assertThat(savedImage)
                .as("Should return image with all parameters and ID")
                .isEqualToIgnoringGivenFields(retrievedImage.get(), "id")
                .isEqualToIgnoringGivenFields(image, "id")
                .hasFieldOrProperty("id");
    }

    private Image getSampleImage(String token) {
        Image image = new Image();
        image.setToken(token);
        image.setIsActive(true);
        image.setIsPublic(true);
        image.setUploadTime(LocalDateTime.now());

        return image;
    }

    private User getSampleUser(String username) {
        User user = new User();
        user.setUsername(username);

        //   filling mandatory NOT NULL fields
        user.setPassword("password");
        user.setEmail(username + "@example.com");
        user.setRegisterTime(LocalDateTime.now());
        user.setIsActive(true);
        return user;
    }
}
