package com.ewsie.allpic.image.service;

import java.io.InputStream;
import java.util.Map;

public interface UploadFileService {

    void uploadFile(InputStream fileStream, String filename);

    void uploadFile(InputStream fileStream, String filename, String mimeType);

    void uploadFile(InputStream fileStream, String filename, Map<String, String> metadata);

    void uploadFile(InputStream fileStream, String filename, Map<String, String> metadata, String mimeType);
}
