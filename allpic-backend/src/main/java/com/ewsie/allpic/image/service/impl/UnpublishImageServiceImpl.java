package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.image.service.ImageService;
import com.ewsie.allpic.image.service.UnpublishImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnpublishImageServiceImpl implements UnpublishImageService {

    private final ImageService imageService;

    @Override
    public void hideImageByToken(String token) throws NullPointerException {
        Optional<Image> image = Optional.ofNullable(imageService.findByToken(token));
        if (image.isEmpty()) {
            throw new NullPointerException("No image of token " + token + " available");
        }

        image.get().setIsActive(false);
        imageService.save(image.get());
    }
}
