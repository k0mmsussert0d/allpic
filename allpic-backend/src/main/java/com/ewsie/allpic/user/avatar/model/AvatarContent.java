package com.ewsie.allpic.user.avatar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

import static lombok.AccessLevel.*;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AvatarContent {
    InputStream content;
    String contentType;
    long contentLength;
}
