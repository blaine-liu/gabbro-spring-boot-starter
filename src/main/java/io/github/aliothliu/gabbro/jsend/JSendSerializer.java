package io.github.aliothliu.gabbro.jsend;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * JSend 序列化对象
 *
 * @author liubin
 */
public class JSendSerializer extends JsonSerializer<JSend> {

    @Override
    public void serialize(JSend jSend, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName(JSend.Fields.status);
        jsonGenerator.writeString(jSend.getStatus().name());

        jsonGenerator.writeFieldName(JSend.Fields.code);
        if (Objects.isNull(jSend.getCode())) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeNumber(jSend.getCode());
        }

        jsonGenerator.writeFieldName(JSend.Fields.message);
        jsonGenerator.writeString(jSend.getMessage());

        jsonGenerator.writeFieldName(JSend.Fields.data);
        jsonGenerator.writeObject(jSend.getData());

        jsonGenerator.writeEndObject();
    }
}
