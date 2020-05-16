package com.ewsie.allpic.user.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestBody {
    String username;
    String password;
}
