package com.ewsie.allpic.image.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImagePreviewDetails {
    String token;
    String title;
    String thumbnail;
}
