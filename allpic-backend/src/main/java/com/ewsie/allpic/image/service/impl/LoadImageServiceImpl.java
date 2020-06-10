package com.ewsie.allpic.image.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImageDTOWithContent;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.LoadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadImageServiceImpl implements LoadImageService {

    private final AppConfig appConfig;
    private final ImageDTOService imageDTOService;

    @Override
    public ImageDTOWithContent loadImage(String token) throws NullPointerException, SdkClientException {
        ImageDTO requestedImageDto = getImageDetails(token);

        S3Object fullObject = getImageObjectFromS3(token);

        ImageDTOWithContent res = ImageDTOWithContent.builder()
                .imageDTO(requestedImageDto)
                .content(fullObject.getObjectContent())
                .contentType(fullObject.getObjectMetadata().getContentType())
                .contentLength(fullObject.getObjectMetadata().getContentLength())
                .build();

        return res;
    }

    @Override
    public ImageDTOWithContent loadThumb(String token) throws NullPointerException, SdkClientException {
        ImageDTO requestedImageDto = getImageDetails(token);

        S3Object thumbObject = getImageObjectFromS3(token + "_thumb");

        ImageDTOWithContent res = ImageDTOWithContent.builder()
                .imageDTO(requestedImageDto)
                .content(thumbObject.getObjectContent())
                .contentType(thumbObject.getObjectMetadata().getContentType())
                .contentLength(thumbObject.getObjectMetadata().getContentLength())
                .build();

        return res;
    }

    private ImageDTO getImageDetails(String token) {
        Optional<ImageDTO> requestedImageDto = Optional.ofNullable(imageDTOService.findByToken(token));
        if (requestedImageDto.isEmpty()) {
            throw new NullPointerException("Content identified by " + token + " not found in database");
        }

        return requestedImageDto.get();
    }

    private S3Object getImageObjectFromS3(String token) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(appConfig.getAwsRegion())
                .build();

        return s3Client.getObject(new GetObjectRequest(appConfig.getS3StorageBucket(), token));
    }
}
