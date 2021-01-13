package com.ewsie.allpic.image.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.ImageThumbnailService;
import com.ewsie.allpic.image.service.ImageTokenService;
import com.ewsie.allpic.image.service.SaveImageService;
import com.ewsie.allpic.user.model.UserDTO;
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
    private final Map<String, String> extensionToMimeType;
    private final ImageThumbnailService imageThumbnailService;

    @Override
    public ImageDTO saveImage(InputStream imageFileInputStream, String imageFilename, String title, boolean isPublic, UserDTO uploader)
            throws IllegalArgumentException, SdkClientException, IOException {

        String token = getUniqueImageToken();
        String mimeType = getFileMimeType(imageFilename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageFileInputStream.transferTo(baos);
        InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());
        InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());

        ImageDTO savedImageDTO = getPersistedImageDto(title, isPublic, uploader, token);
        File thumbnailImage = imageThumbnailService.generateThumbnail(inputStream1, token, getFileExtension(imageFilename));

        uploadImageToS3(inputStream2, token, mimeType);
        uploadImageToS3(new FileInputStream(thumbnailImage), token + "_thumb", mimeType);
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

    private void uploadImageToS3(InputStream imageStream, String token, String mimeType) throws IOException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(appConfig.getAwsRegion())
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(mimeType);
        metadata.addUserMetadata("token", token);
        PutObjectRequest request = new PutObjectRequest(appConfig.getS3StorageBucket(), token, imageStream, metadata);
        s3Client.putObject(request);
    }
}
