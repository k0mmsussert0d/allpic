package com.ewsie.allpic.user.role;

import com.ewsie.allpic.user.role.utils.CustomRoleDTORoleNameSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class RoleDTO {

    int id;
    String role;
}
