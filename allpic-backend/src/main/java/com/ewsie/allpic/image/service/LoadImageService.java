package com.ewsie.allpic.image.service;

import com.amazonaws.SdkClientException;
import com.ewsie.allpic.image.model.ImageDTOWithContent;

public interface LoadImageService {
    ImageDTOWithContent loadImage(String token) throws IllegalArgumentException, SdkClientException;
}
