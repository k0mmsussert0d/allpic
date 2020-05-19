package com.ewsie.allpic.image.service;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.session.model.SessionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SaveImageService {

    ImageDTO saveImage(MultipartFile image, SessionDTO sessionDTO) throws IOException;
}
