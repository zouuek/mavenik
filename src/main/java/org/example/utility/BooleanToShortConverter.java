package org.example.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToShortConverter implements AttributeConverter<Boolean,Short> {

    @Override
    public Short convertToDatabaseColumn(Boolean aBoolean) {
        if(aBoolean == null){
            return  null;
        }
        return aBoolean ? (short) 1 : (short) 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Short aShort) {
        return aShort != null && aShort == 1;
    }
}
