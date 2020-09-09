package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.ImageService;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ImageDTOServiceImplTest {

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    ImageDTOService imageDTOService;

    @BeforeEach
    public void setUp() {
        imageDTOService = new ImageDTOServiceImpl(imageService, modelMapper);
    }

    @Test
    public void whenFindById_thenReturnImage() {
        // given
        Image image = getSampleImage("TOKEN");
        Image saved = imageService.save(image);

        // when
        ImageDTO found = imageDTOService.findById(saved.getId());

        // then
        assertThat(found)
                .as("Should have same properties as saved image")
                .isEqualToComparingFieldByField(saved);
    }

    @Test
    public void whenFindByToken_thenReturnImage() {
        // given
        Image image = getSampleImage("TOKEN");
        Image saved = imageService.save(image);

        // when
        ImageDTO found = imageDTOService.findByToken(image.getToken());

        // then
        assertThat(found)
                .as("Should have same properties as saved image")
                .isEqualToComparingFieldByField(saved);
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
        imageService.save(image1);
        imageService.save(image2);

        // when
        List<ImageDTO> found = imageDTOService.findAllUploadedBy(modelMapper.map(user, UserDTO.class));

        // then
        assertThat(found)
                .as("Should return all images uploaded by the user")
                .extracting(imageDTO -> modelMapper.map(imageDTO, Image.class))
                .usingElementComparatorIgnoringFields("id")
                .containsAll(List.of(image1, image2));
    }

    @Test
    public void whenFindAllOrderByUploadedTimeDesc() {
        // given
        Image image1 = getSampleImage("TOKEN1");
        Image image2 = getSampleImage("TOKEN2");
        Image image3 = getSampleImage("TOKEN3");
        image1.setUploadTime(LocalDateTime.now());
        image2.setUploadTime(LocalDateTime.now().minusMinutes(1));
        image3.setUploadTime(LocalDateTime.now().minusMinutes(2));

        imageService.save(image1);
        imageService.save(image2);
        imageService.save(image3);

        // when
        List<ImageDTO> found = imageDTOService.findAllOrderByUploadedTimeDesc();

        // then
        assertThat(found)
                .as("Should return all images ordered by upload time in descending order")
                .extracting(imageDTO -> modelMapper.map(imageDTO, Image.class))
                .usingElementComparatorIgnoringFields("id")
                .containsExactly(image1, image2, image3);
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
