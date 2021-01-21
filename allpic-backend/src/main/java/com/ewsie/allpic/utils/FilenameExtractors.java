package com.ewsie.allpic.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilenameExtractors {

    private final Map<String, String> extensionToMimeType;

    public String getFileMimeType(String filename) {
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

    public String getFileExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(i + 1);
        }

        return null;
    }
}
