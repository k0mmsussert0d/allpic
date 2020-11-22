package com.ewsie.allpic.image.service;

import com.ewsie.allpic.image.model.ImageDTO;

public interface UnpublishImageService {
    ImageDTO hideImageByToken(String token) throws NullPointerException;
}
