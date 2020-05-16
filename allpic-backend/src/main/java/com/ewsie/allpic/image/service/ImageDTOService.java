package com.ewsie.allpic.image.service;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.model.UserDTO;

import java.util.List;

public interface ImageDTOService {
    ImageDTO findById(Long id);

    List<ImageDTO> findAllUploadedBy(UserDTO uploader);

    ImageDTO save(ImageDTO image);
}
