package com.sathish.productservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    @Column(nullable = false, unique = true, name = "category_name")
    private String name;
    private String description;
    @OneToMany(fetch = jakarta.persistence.FetchType.EAGER)
    private List<Product> featuredProducts;
    @OneToMany(mappedBy = "category")
    private List<Product> allProducts;
    private int totalProductsCount;
}
