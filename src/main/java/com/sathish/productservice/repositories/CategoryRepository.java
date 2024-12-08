package com.sathish.productservice.repositories;

import com.sathish.productservice.models.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Category save(Category category);

   // List<Category> findByIdGreaterThanAndNameGreaterThan(Long idIsGreaterThan, String nameIsGreaterThan);
}
