package com.ewsie.allpic.image.service;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.user.model.User;

import java.util.List;

public interface ImageService {

    Image findById(Long id);

    List<Image> findAllUploadedBy(User uploader);

    Image save(Image image);
}
