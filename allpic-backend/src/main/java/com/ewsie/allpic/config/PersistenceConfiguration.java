package com.ewsie.allpic.config;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImagePreviewDetails;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class PersistenceConfiguration {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<ImageDTO, ImagePreviewDetails> imageDTOToImagePreviewDetails =
                modelMapper.createTypeMap(ImageDTO.class, ImagePreviewDetails.class);


        imageDTOToImagePreviewDetails.addMappings(mapper -> {
            mapper.map(ImageDTO::getToken, ImagePreviewDetails::setToken);
            mapper.map(ImageDTO::getTitle, ImagePreviewDetails::setTitle);
        });

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        return modelMapper;
    }
}
