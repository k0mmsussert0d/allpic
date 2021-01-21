package com.ewsie.allpic.user.avatar.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.user.avatar.model.AvatarContent;
import com.ewsie.allpic.user.avatar.service.LoadAvatarService;
import com.ewsie.allpic.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadAvatarServiceImpl implements LoadAvatarService {

    private final AppConfig appConfig;

    @Override
    public AvatarContent loadAvatar(UserDTO userDTO) {
        S3Object object = getImageObjectFromS3(userDTO.getUsername());

        return AvatarContent.builder()
                .content(object.getObjectContent())
                .contentLength(object.getObjectMetadata().getContentLength())
                .contentType(object.getObjectMetadata().getContentType())
                .build();
    }

    private S3Object getImageObjectFromS3(String username) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(appConfig.getAwsRegion())
                .build();

        return s3Client.getObject(new GetObjectRequest(appConfig.getS3StorageBucket(), username + "_avatar"));
    }
}
