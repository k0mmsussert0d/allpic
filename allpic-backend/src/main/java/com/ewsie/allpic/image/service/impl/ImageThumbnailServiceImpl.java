package com.ewsie.allpic.image.service.impl;

import com.ewsie.allpic.image.service.ImageThumbnailService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class ImageThumbnailServiceImpl implements ImageThumbnailService {

    @Override
    public void generateThumbnail(InputStream file, OutputStream output) throws IOException {
        Thumbnails.of(file)
                .size(250, 250)
                .toOutputStream(output);
    }
}
