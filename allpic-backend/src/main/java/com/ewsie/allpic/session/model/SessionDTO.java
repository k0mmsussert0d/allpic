package com.ewsie.allpic.session.model;

import com.ewsie.allpic.user.model.UserDTO;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class SessionDTO {
    Long id;
    String sessionIdentifier;
    LocalDateTime validUntil;
    UserDTO user;
}
