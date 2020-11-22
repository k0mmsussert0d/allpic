package com.ewsie.allpic.user.role.utils;

import com.ewsie.allpic.user.role.RoleDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomRoleDTORoleNameSerializer
        extends StdSerializer<RoleDTO> {

    public CustomRoleDTORoleNameSerializer() {
        this(null);
    }

    public CustomRoleDTORoleNameSerializer(Class<RoleDTO> t) {
        super(t);
    }

    @Override
    public void serialize(RoleDTO roleDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(roleDTO.getRoleName());
    }
}
