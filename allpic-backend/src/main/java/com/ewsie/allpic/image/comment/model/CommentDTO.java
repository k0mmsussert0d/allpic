package com.ewsie.allpic.image.comment.model;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.utils.CustomUserDTOUsernameSerializer;
import com.ewsie.allpic.utils.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    Long id;
    String message;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    LocalDateTime timeAdded;
    Boolean isPublic;

    UserDTO author;
}
