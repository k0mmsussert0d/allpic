package com.ewsie.allpic.user.utils;

import com.ewsie.allpic.user.model.UserDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomUserDTOUsernameSerializer
        extends StdSerializer<UserDTO> {

    public CustomUserDTOUsernameSerializer() {
        this(null);
    }

    public CustomUserDTOUsernameSerializer(Class<UserDTO> t) {
        super(t);
    }

    @Override
    public void serialize(UserDTO userDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(userDTO.getUsername());
    }
}
