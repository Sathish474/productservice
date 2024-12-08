package com.sathish.productservice.repositories;

import com.sathish.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Update and save is done using save method
    @Override
    Product save(Product product);

    @Override
    void delete(Product entity);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Boolean deleteById(long id);

    Optional<List<Product>> findByCategory_NameEquals(String name);

    @Query("select p from Product p where p.category.name = :s")
    List<Product> sathishNalabothula(@Param("s") String s);
    @Query("select p from Product p where p.id > :id")
    List<Product> someThing(@Param("id") Long id);
}
