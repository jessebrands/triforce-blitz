package com.triforceblitz.triforceblitz.util.converters;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToVersionConverter implements Converter<String, TriforceBlitzVersion> {
    @Override
    public TriforceBlitzVersion convert(String source) {
        return TriforceBlitzVersion.fromString(source);
    }
}
