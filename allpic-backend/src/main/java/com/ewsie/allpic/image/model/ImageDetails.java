package com.ewsie.allpic.image.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class ImageDetails {
    String title;
    boolean isPublic;
}
