package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.image.repository.ImageRepository;
import com.ewsie.allpic.image.service.ImageService;
import com.ewsie.allpic.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Image findByPath(String path) {
        return imageRepository.findByPath(path).orElse(null);
    }

    @Override
    public List<Image> findAllUploadedBy(User uploader) {
        return imageRepository.findAllByUploader(uploader).orElse(null);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }
}
