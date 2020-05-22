package com.ewsie.allpic.image.service.impl;

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
import com.ewsie.allpic.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveImageServiceImpl implements SaveImageService {

    private final AppConfig appConfig;
    private final ImageTokenService imageTokenService;
    private final ImageDTOService imageDTOService;
    private final Map<String, String> extensionToMimeType;

    @Override
    public ImageDTO saveImage(MultipartFile image, String title, @NotNull boolean isPublic, UserDTO uploader)
            throws IllegalArgumentException, SdkClientException, IOException {

        String token = getUniqueImageToken();
        String mimeType = getFileMimeType(image.getOriginalFilename());

        ImageDTO savedImageDTO = getPersistedImageDto(title, uploader, token);

        uploadImageToS3(image, token, mimeType);

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

    private String getFileMimeType(String filename) {
        Optional<String> extension = Optional.ofNullable(getFileExtension(filename));
        if (extension.isEmpty()) {
            throw new IllegalArgumentException("Uploading files with no extension is not allowed");
        }

        Optional<String> mimeType = Optional.ofNullable(extensionToMimeType.get(extension.get()));
        if (mimeType.isEmpty()) {
            throw new IllegalArgumentException("File type " + extension.get() + " is not allowed");
        }

        return mimeType.get();
    }

    private String getFileExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(i + 1);
        }

        return null;
    }

    private ImageDTO getPersistedImageDto(String title, UserDTO uploader, String token) {
        ImageDTO imageDTO = ImageDTO.builder()
                .token(token)
                .title(title)
                .uploadTime(LocalDateTime.now())
                .isActive(true)
                .uploader(uploader)
                .build();
        return imageDTOService.save(imageDTO);
    }

    private void uploadImageToS3(MultipartFile image, String token, String mimeType) throws IOException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(mimeType);
        metadata.addUserMetadata("token", token);
        PutObjectRequest request = new PutObjectRequest("allpic-public", token, image.getInputStream(), metadata);
        s3Client.putObject(request);
    }
}
