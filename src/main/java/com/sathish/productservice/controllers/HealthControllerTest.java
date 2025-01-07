package com.sathish.productservice.controllers;

import com.sathish.productservice.dto.products.GetAllProductResponseDto;
import com.sathish.productservice.dto.products.GetProductDto;
import com.sathish.productservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HealthControllerTest {
    @GetMapping("")
    public String getAllProducts() {
        return "Hello, I am Up and Running!!!!!";
    }
}
