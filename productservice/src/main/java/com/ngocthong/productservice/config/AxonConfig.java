package com.ngocthong.productservice.config;

import com.ngocthong.productservice.command.event.ProductUpdatedEvent;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream() {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        // Ignore serializing "randomInt" field of ProductUpdatedEvent class
                        if (definedIn == ProductUpdatedEvent.class && "randomInt".equals(fieldName)) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        xStream.allowTypesByWildcard(new String[] {
                "com.ngocthong.**",
                "com.quoctoan.**"
        });
        return xStream;
    }
}
