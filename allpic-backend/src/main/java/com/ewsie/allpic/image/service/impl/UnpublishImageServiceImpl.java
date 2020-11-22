package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.ImageService;
import com.ewsie.allpic.image.service.UnpublishImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnpublishImageServiceImpl implements UnpublishImageService {

    private final ImageService imageService;
    private final ImageDTOService imageDTOService;

    @Override
    public ImageDTO hideImageByToken(String token) throws NullPointerException {
        Optional<ImageDTO> imageDTO = Optional.ofNullable(imageDTOService.findByToken(token));
        if (imageDTO.isEmpty()) {
            throw new NullPointerException("No image of token " + token + " available");
        }

        imageDTO.get().setIsActive(false);
        imageDTOService.save(imageDTO.get());

        return imageDTO.get();
    }
}
