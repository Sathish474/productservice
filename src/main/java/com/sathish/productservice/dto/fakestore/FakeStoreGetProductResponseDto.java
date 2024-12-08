package com.sathish.productservice.dto.fakestore;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreGetProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Double price;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        return getProduct(this, product);
    }

    public static Product getProduct(FakeStoreGetProductResponseDto fakeStoreGetProductResponseDto, Product product) {
        product.setId(fakeStoreGetProductResponseDto.getId());
        product.setTitle(fakeStoreGetProductResponseDto.getTitle());
        product.setPrice(fakeStoreGetProductResponseDto.getPrice());
        product.setDescription(fakeStoreGetProductResponseDto.getDescription());
        Category category = new Category();
        category.setDescription(product.getDescription());
        category.setName(fakeStoreGetProductResponseDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(fakeStoreGetProductResponseDto.getImage());
        return product;
    }
}
