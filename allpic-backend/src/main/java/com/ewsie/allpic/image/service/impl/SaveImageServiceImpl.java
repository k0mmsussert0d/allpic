package com.ewsie.allpic.image.service.impl;

import com.amazonaws.SdkClientException;
import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.*;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.utils.FilenameExtractors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveImageServiceImpl implements SaveImageService {

    private final AppConfig appConfig;
    private final ImageTokenService imageTokenService;
    private final ImageDTOService imageDTOService;
    private final ImageThumbnailService imageThumbnailService;
    private final UploadFileService uploadFileService;
    private final FilenameExtractors filenameExtractors;

    @Override
    public ImageDTO saveImage(InputStream imageFileInputStream, String imageFilename, String title, boolean isPublic, UserDTO uploader)
            throws IllegalArgumentException, SdkClientException, IOException {

        String token = getUniqueImageToken();
        String mimeType = filenameExtractors.getFileMimeType(imageFilename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageFileInputStream.transferTo(baos);
        InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());
        InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());

        ImageDTO savedImageDTO = getPersistedImageDto(title, isPublic, uploader, token);
        File thumbnailImage = imageThumbnailService.generateThumbnail(inputStream1);

        Map<String, String> mainObjectMetadata = Map.of("token", token, "type", "image");
        Map<String, String> thumbObjectMetadata = Map.of("token", token, "type", "thumb");
        uploadFileService.uploadFile(inputStream2, token, mainObjectMetadata, mimeType);
        uploadFileService.uploadFile(new FileInputStream(thumbnailImage), token + "_thumb", thumbObjectMetadata, "image/jpeg");
        thumbnailImage.deleteOnExit();

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

    private ImageDTO getPersistedImageDto(String title, boolean isPublic, UserDTO uploader, String token) {
        ImageDTO imageDTO = ImageDTO.builder()
                .token(token)
                .title(title)
                .uploadTime(LocalDateTime.now())
                .isPublic(isPublic)
                .isActive(true)
                .uploader(uploader)
                .build();
        return imageDTOService.save(imageDTO);
    }
}
