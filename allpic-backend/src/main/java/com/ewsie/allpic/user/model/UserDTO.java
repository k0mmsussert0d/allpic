package com.ewsie.allpic.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserDTO {

    Long id;
    String username;
    String password;
    String email;
    Time registerTime;
    Boolean isActive;
}
