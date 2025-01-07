package com.sathish.productservice.converters;

import com.sathish.productservice.dto.search.FilterDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilterDtoConverter implements Converter<String, FilterDto> {

    @Override
    public FilterDto convert(String source) {
        String[] parts = source.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid filter format. Expected format: key:val1,val2");
        }
        FilterDto filterDto = new FilterDto();
        filterDto.setKey(parts[0]);
        filterDto.setValues(List.of(parts[1].split(",")));
        return filterDto;
    }
}
