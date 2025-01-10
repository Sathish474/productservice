package com.sathish.productservice.dto.products;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GetAllProductResponseDto {
    private List<GetProductDto> products;
}
