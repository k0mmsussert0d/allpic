package com.ewsie.allpic.image.service;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.model.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;


public interface SaveImageService {

    ImageDTO saveImage(
            MultipartFile image,
            String title,
            @NotNull boolean isPublic,
            UserDTO uploader
    ) throws IllegalArgumentException, IOException;
}
