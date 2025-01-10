package com.sathish.productservice.controllers;

import com.sathish.productservice.dto.exceptions.ErrorResponseDto;
import com.sathish.productservice.dto.products.*;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final RestTemplate restTemplate;

    public ProductController(@Qualifier("databaseProductService") ProductService productService, RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        logger.info("Creating product with name: {}", createProductRequestDto.toString());
        Product product = productService.createProduct(createProductRequestDto.toProduct());
        logger.info("Product created with id: {}", product.getId());
        return CreateProductResponseDto.from(product);
    }

    @GetMapping("")
    public GetAllProductResponseDto getAllProducts() {
        logger.info("Getting all products");
        boolean isValid = Boolean.TRUE.equals(restTemplate.getForObject("http://userservice/auth/validate?token=hello", Boolean.class));
        System.out.println("IS Valid Response from user service eureka client" + isValid);
        List<Product> productList = productService.getAllProducts();
        GetAllProductResponseDto getAllProductResponseDto = new GetAllProductResponseDto();
        List<GetProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            productDtoList.add(GetProductDto.from(product));
        }
        getAllProductResponseDto.setProducts(productDtoList);
        logger.info("Returning all products");
        return getAllProductResponseDto;
    }

    @GetMapping("/{id}")
    public GetProductResponseDto getProduct(@PathVariable("id") Long id){
        logger.info("Getting product with id: {}", id);
        Product product = productService.getProduct(id);
        GetProductResponseDto getProductResponseDto = new GetProductResponseDto();
        GetProductDto getProductDto = GetProductDto.from(product);
        getProductResponseDto.setProductDto(getProductDto);
        logger.info("Returning product with id: {}. Product {} ", id, getProductDto.toString());
        return getProductResponseDto;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteProduct(@PathVariable("id") Long id){
        logger.info("Deleting product with id: {}", id);
        return productService.deleteProduct(id);
    }


    @PatchMapping("/{id}")
    public PatchProductResponseDto updateProduct(@PathVariable("id") Long id, @RequestBody CreateProductDto product) {
        logger.info("Updating product with id: {}. UpdateProduct: {}", id, product.toString());
        Product product1 = productService.paritialUpdateProduct(id, product.toProduct());

        PatchProductResponseDto patchProductResponseDto = new PatchProductResponseDto();
        patchProductResponseDto.setDto(GetProductDto.from(product1));
        logger.info("Returning updated product with id: {}. UpdatedProduct: {}", id, patchProductResponseDto.toString());
        return patchProductResponseDto;
    }

    @GetMapping("/name/{name}")
    public List<GetProductResponseDto> getProductByName(@PathVariable("name") String name) {
        logger.info("Getting product with name: {}", name);
        List<Product> product = productService.getProductByCategoryName(name);
        List<GetProductResponseDto> getProductResponseDtos = new ArrayList<>();
        for (Product product1 : product) {
            GetProductResponseDto getProductResponseDto = new GetProductResponseDto();
            GetProductDto getProductDto = GetProductDto.from(product1);
            getProductResponseDto.setProductDto(getProductDto);
            getProductResponseDtos.add(getProductResponseDto);
        }
        logger.info("Returning product with name: {}. Product {} ", name, getProductResponseDtos.toString());
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
