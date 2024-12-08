package com.sathish.productservice.services;

import com.sathish.productservice.dto.fakestore.FakeStoreCreateProductRequestDto;
import com.sathish.productservice.dto.fakestore.FakeStoreGetProductResponseDto;
import com.sathish.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service("fakeStoreProductService")
public class ProductServiceFakeStoreImpl implements ProductService {


    private RestTemplate restTemplate;

    public ProductServiceFakeStoreImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreCreateProductRequestDto requestDto = FakeStoreCreateProductRequestDto.from(product);

        FakeStoreGetProductResponseDto fakeStoreGetProductResponseDto = restTemplate.postForObject("https://fakestoreapi.com/products", requestDto, FakeStoreGetProductResponseDto.class);

        Product product1 = new Product();
        assert fakeStoreGetProductResponseDto != null;
        return FakeStoreGetProductResponseDto.getProduct(fakeStoreGetProductResponseDto, product1);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreGetProductResponseDto[] getAllFakeStoreProductResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreGetProductResponseDto[].class);
        assert getAllFakeStoreProductResponseDto != null;
        List<FakeStoreGetProductResponseDto> responseDtoList = Stream.of(getAllFakeStoreProductResponseDto).toList();

        List<Product> products = new ArrayList<>();
        for (FakeStoreGetProductResponseDto fakeStoreGetProductResponseDto : responseDtoList) {
            products.add(fakeStoreGetProductResponseDto.toProduct());
        }
        throw new RuntimeException("Internal Server Error");
        //return products;
    }

    @Override
    public Product paritialUpdateProduct(Long productId, Product product) {
        throw new RuntimeException("Internal Server Error");
/*        HttpEntity<FakeStoreCreateProductRequestDto> requestDtoHttpEntity = new HttpEntity<>(new FakeStoreCreateProductRequestDto());
        ResponseEntity<FakeStoreGetProductResponseDto> fakeStoreGetProductResponseDto = restTemplate.exchange(
                "https://fakestoreapi.com/products" + productId,
                HttpMethod.PATCH,
                requestDtoHttpEntity,
                FakeStoreGetProductResponseDto.class
        );

        return fakeStoreGetProductResponseDto.getBody().toProduct();*/
    }

    @Override
    public Product getProduct(Long id) {
        FakeStoreGetProductResponseDto fakeStoreGetProductResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreGetProductResponseDto.class, id);

        assert fakeStoreGetProductResponseDto != null;
        return FakeStoreGetProductResponseDto.getProduct(fakeStoreGetProductResponseDto, new Product());
    }

    @Override
    public Boolean deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/{id}", id);
        return true;
    }

    @Override
    public List<Product> getProductByCategoryName(String name) {
        return null;
    }


}
