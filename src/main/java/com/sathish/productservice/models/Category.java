package com.sathish.productservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @JsonManagedReference
    private List<Product> featuredProducts;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> allProducts;
    private int totalProductsCount;
}
