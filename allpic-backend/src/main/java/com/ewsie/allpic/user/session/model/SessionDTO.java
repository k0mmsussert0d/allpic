package com.ewsie.allpic.user.session.model;

import com.ewsie.allpic.user.model.UserDTO;
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
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    Long id;
    String sessionIdentifier;
    LocalDateTime validUntil;
    UserDTO user;
}
