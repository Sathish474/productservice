package com.sathish.productservice.dto.fakestore;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllFakeStoreProductResponseDto {
    private List<FakeStoreGetProductResponseDto> products;
}
