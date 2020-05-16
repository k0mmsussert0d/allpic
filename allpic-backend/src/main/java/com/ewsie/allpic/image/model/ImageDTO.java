package com.ewsie.allpic.image.model;

import com.ewsie.allpic.user.model.UserDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ImageDTO {
    Long id;
    String path;
    String title;
    LocalDateTime uploadTime;
    Boolean isActive;
    UserDTO uploader;
}
