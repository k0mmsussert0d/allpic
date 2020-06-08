package com.ewsie.allpic.image.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ImageThumbnailService {
    File generateThumbnail(InputStream file, String token, String extension) throws IOException;
}
