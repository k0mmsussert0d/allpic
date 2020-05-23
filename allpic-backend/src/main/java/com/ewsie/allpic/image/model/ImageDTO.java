package com.ewsie.allpic.image.model;

import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.utils.CustomUserDTOUsernameSerializer;
import com.ewsie.allpic.utils.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@JsonSerialize
public class ImageDTO {
    Long id;
    String token;
    String title;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    LocalDateTime uploadTime;
    Boolean isPublic;
    Boolean isActive;

    @JsonSerialize(using = CustomUserDTOUsernameSerializer.class)
    UserDTO uploader;

    @JsonIgnore
    List<CommentDTO> comments;

    public void addComment(CommentDTO commentDTO) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }

        this.comments.add(commentDTO);
    }
}
