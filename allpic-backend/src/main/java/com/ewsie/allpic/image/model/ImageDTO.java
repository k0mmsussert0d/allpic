package com.ewsie.allpic.image.model;

import com.ewsie.allpic.user.model.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@JsonSerialize
public class ImageDTO {
    Long id;
    String token;
    String title;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    LocalDateTime uploadTime;
    Boolean isPublic;
    Boolean isActive;

    @JsonIgnore
    UserDTO uploader;

    public static class CustomLocalDateTimeSerializer
            extends StdSerializer<LocalDateTime> {

        private static DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        public CustomLocalDateTimeSerializer() {
            this(null);
        }

        public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
            super(t);
        }

        @Override
        public void serialize(
                LocalDateTime value,
                JsonGenerator gen,
                SerializerProvider arg2)
                throws IOException, JsonProcessingException {

            gen.writeString(formatter.format(value));
        }
    }
}
