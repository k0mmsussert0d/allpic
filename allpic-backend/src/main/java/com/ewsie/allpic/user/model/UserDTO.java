package com.ewsie.allpic.user.model;

import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.role.utils.CustomRoleDTORoleNameSerializer;
import com.ewsie.allpic.utils.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@JsonSerialize
public class UserDTO {

    Long id;
    String username;

    @JsonIgnore
    String password;
    String email;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    LocalDateTime registerTime;
    Boolean isActive;

    @JsonSerialize(using = CustomRoleDTORoleNameSerializer.class)
    RoleDTO role;
}
