package com.sathish.productservice.dto.fakestore;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreCreateProductRequestDto {
    private String title;
    private String description;
    private String image;
    private Double price;
    private String category;

    public static FakeStoreCreateProductRequestDto from(Product product) {
        FakeStoreCreateProductRequestDto requestDto = new FakeStoreCreateProductRequestDto();
        requestDto.setCategory(product.getCategory().getName());
        requestDto.setImage(product.getImageUrl());
        requestDto.setPrice(product.getPrice());
        requestDto.setDescription(product.getDescription());
        requestDto.setTitle(product.getTitle());
        return requestDto;
    }
}
