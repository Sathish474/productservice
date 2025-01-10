package com.sathish.productservice.dto.products;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchProductResponseDto {
    private GetProductDto dto;
}
