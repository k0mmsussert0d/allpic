package com.ewsie.allpic.image.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ImageThumbnailService {
    void generateThumbnail(InputStream file, OutputStream output) throws IOException;
}
