package com.ewsie.allpic.image.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.service.UploadFileService;
import com.ewsie.allpic.utils.FilenameExtractors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final AppConfig appConfig;
    private final FilenameExtractors filenameExtractors;

    @Override
    public void uploadFile(InputStream fileStream, String filename) {
        Map<String, String> metadata = new HashMap<>();
        String mimeType = filenameExtractors.getFileMimeType(filename);

        uploadFile(fileStream, filename, metadata, mimeType);
    }

    @Override
    public void uploadFile(InputStream fileStream, String filename, String mimeType) {
        Map<String, String> metadata = new HashMap<>();
        uploadFile(fileStream, filename, metadata, mimeType);
    }

    @Override
    public void uploadFile(InputStream fileStream, String filename, Map<String, String> metadata) {
        uploadFile(fileStream, filename, metadata, filenameExtractors.getFileMimeType(filename));
    }

    public void uploadFile(InputStream fileStream, String filename, Map<String, String> metadata, String mimeType) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(appConfig.getAwsRegion())
                .build();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(mimeType);
        if (!metadata.isEmpty()) {
            objectMetadata.setUserMetadata(metadata);
        }
        PutObjectRequest request = new PutObjectRequest(appConfig.getS3StorageBucket(), filename, fileStream, objectMetadata);
        s3Client.putObject(request);
    }
}
