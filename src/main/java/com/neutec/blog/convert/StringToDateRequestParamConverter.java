package com.neutec.blog.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StringToDateRequestParamConverter implements Converter<String, Date> {
    @Override
    public Date convert(@NonNull String source) {
        return new Date(Long.parseLong(source));
    }
}