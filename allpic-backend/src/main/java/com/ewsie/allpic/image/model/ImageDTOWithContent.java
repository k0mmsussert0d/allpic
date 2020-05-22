package com.ewsie.allpic.image.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class ImageDTOWithContent {
    ImageDTO imageDTO;
    InputStream content;
    String contentType;
    long contentLength;
}
