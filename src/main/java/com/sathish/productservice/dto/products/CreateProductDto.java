package com.sathish.productservice.dto.products;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateProductDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryName;

    public static CreateProductDto from(Product product) {
        CreateProductDto dto = new CreateProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        Category category = product.getCategory();
        dto.setCategoryName(category.getName());
        return dto;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.imageUrl);
        Category category = new Category();
        category.setName(this.categoryName);
        product.setCategory(category);
        return product;
    }
}
