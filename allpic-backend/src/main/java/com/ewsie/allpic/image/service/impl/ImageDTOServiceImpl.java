package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.ImageService;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageDTOServiceImpl implements ImageDTOService {

    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public ImageDTO findById(Long id) {
        Optional<Image> image = Optional.ofNullable(imageService.findById(id));

        if (image.isPresent()) {
            return modelMapper.map(image.get(), ImageDTO.class);
        }

        return null;
    }

    @Override
    public ImageDTO findByPath(String path) {
        Optional<Image> image = Optional.ofNullable(imageService.findByPath(path));

        if (image.isPresent()) {
            return modelMapper.map(image.get(), ImageDTO.class);
        }

        return null;
    }

    @Override
    public List<ImageDTO> findAllUploadedBy(UserDTO uploaderDto) {
        User uploader = modelMapper.map(uploaderDto, User.class);
        Optional<List<Image>> images = Optional.ofNullable(imageService.findAllUploadedBy(uploader));

        if (images.isPresent()) {
            Type listType = new TypeToken<List<ImageDTO>>(){}.getType();

            return modelMapper.map(images.get(), listType);
        }

        return null;
    }

    @Override
    public ImageDTO save(ImageDTO image) {
        Image savedImage = imageService.save(modelMapper.map(image, Image.class));
        return modelMapper.map(savedImage, ImageDTO.class);
    }
}
