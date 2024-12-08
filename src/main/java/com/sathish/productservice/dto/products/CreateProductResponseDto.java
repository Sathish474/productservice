package com.sathish.productservice.dto.products;

import com.sathish.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;

    public static CreateProductResponseDto from(Product product) {
        CreateProductResponseDto dto = new CreateProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
}
