package com.ewsie.allpic.user.role;

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
    String roleName;

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof RoleDTO)) { return false; }
        RoleDTO other = (RoleDTO) o;
        return this.id == other.id && this.roleName.equals(other.roleName);
    }
}
