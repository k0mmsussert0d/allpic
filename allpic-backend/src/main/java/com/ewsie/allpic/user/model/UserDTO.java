package com.ewsie.allpic.user.model;

import com.ewsie.allpic.user.role.RoleDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime registerTime;
    Boolean isActive;
    RoleDTO role;
}
