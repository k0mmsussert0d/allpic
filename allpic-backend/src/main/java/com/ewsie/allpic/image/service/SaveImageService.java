package com.ewsie.allpic.image.service;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.model.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;


public interface SaveImageService {

    ImageDTO saveImage(
            InputStream imageFileInputStream,
            String imageFilename, String title,
            boolean isPublic,
            UserDTO uploader
    ) throws IllegalArgumentException, IOException;
}
