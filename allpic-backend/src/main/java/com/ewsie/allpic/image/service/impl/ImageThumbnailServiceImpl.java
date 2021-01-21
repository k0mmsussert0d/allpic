package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.image.service.ImageThumbnailService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ImageThumbnailServiceImpl implements ImageThumbnailService {

    private final AppConfig appConfig;

    @Override
    public File generateThumbnail(InputStream file) throws IOException {
        File output = File.createTempFile("thumb-" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), ".jpg");

        Thumbnails.of(file)
                .size(appConfig.getThumbSize(), appConfig.getThumbSize())
                .crop(Positions.CENTER)
                .toFile(output);

        return output;
    }
}
