package com.sathish.productservice.controllers;

import com.sathish.productservice.dto.exceptions.ErrorResponseDto;
import com.sathish.productservice.dto.products.*;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier("databaseProductService") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        Product product = productService.createProduct(createProductRequestDto.toProduct());

        return CreateProductResponseDto.from(product);
    }

    @GetMapping("")
    public GetAllProductResponseDto getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        GetAllProductResponseDto getAllProductResponseDto = new GetAllProductResponseDto();
        List<GetProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            productDtoList.add(GetProductDto.from(product));
        }
        getAllProductResponseDto.setProducts(productDtoList);
        return getAllProductResponseDto;
    }

    @GetMapping("/{id}")
    public GetProductResponseDto getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        GetProductResponseDto getProductResponseDto = new GetProductResponseDto();
        GetProductDto getProductDto = GetProductDto.from(product);
        getProductResponseDto.setProductDto(getProductDto);
        return getProductResponseDto;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteProduct(@PathVariable("id") Long id){
        return productService.deleteProduct(id);
    }


    @PatchMapping("/{id}")
    public PatchProductResponseDto updateProduct(@PathVariable("id") Long id, @RequestBody CreateProductDto product) {
        Product product1 = productService.paritialUpdateProduct(id, product.toProduct());

        PatchProductResponseDto patchProductResponseDto = new PatchProductResponseDto();
        patchProductResponseDto.setDto(GetProductDto.from(product1));

        return patchProductResponseDto;
    }

    @GetMapping("/name/{name}")
    public List<GetProductResponseDto> getProductByName(@PathVariable("name") String name) {
        List<Product> product = productService.getProductByCategoryName(name);
        List<GetProductResponseDto> getProductResponseDtos = new ArrayList<>();
        for (Product product1 : product) {
            GetProductResponseDto getProductResponseDto = new GetProductResponseDto();
            GetProductDto getProductDto = GetProductDto.from(product1);
            getProductResponseDto.setProductDto(getProductDto);
            getProductResponseDtos.add(getProductResponseDto);
        }
        
        return getProductResponseDtos;
    }

    /*@ExceptionHandler(Exception.class)
    public ErrorResponseDto handleExceptions(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatus("ERROR");
        return errorResponseDto;
    }*/
}
