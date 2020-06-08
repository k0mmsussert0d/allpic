package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.service.ImageThumbnailService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImageThumbnailServiceImpl implements ImageThumbnailService {

    private final AppConfig appConfig;

    @Override
    public File generateThumbnail(InputStream file, String token, String extension) throws IOException {
        File output = File.createTempFile("token-", "-thumb");

        Thumbnails.of(file)
                .size(appConfig.getThumbSize(), appConfig.getThumbSize())
                .toFile(output);

        return output;
    }
}
