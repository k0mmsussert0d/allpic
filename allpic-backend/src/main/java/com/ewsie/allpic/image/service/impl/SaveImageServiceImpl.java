package com.ewsie.allpic.image.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.ImageTokenService;
import com.ewsie.allpic.image.service.SaveImageService;
import com.ewsie.allpic.user.session.model.SessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveImageServiceImpl implements SaveImageService {

    private final AppConfig appConfig;
    private final ImageTokenService imageTokenService;
    private final ImageDTOService imageDTOService;

    @Override
    public ImageDTO saveImage(MultipartFile image, SessionDTO sessionDTO) throws IOException {

        String token = getUniqueImageToken();

        ImageDTO imageDTO = ImageDTO.builder()
                .token(token)
                .title("new title")
                .uploadTime(LocalDateTime.now())
                .isActive(true)
                .build();
        ImageDTO savedImageDTO = imageDTOService.save(imageDTO);

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.EU_CENTRAL_1)
                    .build();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.addUserMetadata("token", token);
            PutObjectRequest request = new PutObjectRequest("allpic-public", token + ".jpg", image.getInputStream(), metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        return savedImageDTO;
    }

    private String getUniqueImageToken() {
        String token = imageTokenService.generateToken(appConfig.getImgTokenLength());
        Optional<ImageDTO> duplicatedTokenImageDto = Optional.ofNullable(imageDTOService.findByToken(token));

        while (duplicatedTokenImageDto.isPresent()) {
            token = imageTokenService.generateToken(appConfig.getImgTokenLength());
            duplicatedTokenImageDto = Optional.ofNullable(imageDTOService.findByToken(token));
        }

        return token;
    }
}
