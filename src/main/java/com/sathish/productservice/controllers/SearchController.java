package com.sathish.productservice.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sathish.productservice.dto.products.GetProductDto;
import com.sathish.productservice.dto.search.FilterDto;
import com.sathish.productservice.dto.search.SearchResponseDto;
import com.sathish.productservice.dto.search.SortingCriteria;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public SearchResponseDto search(@RequestParam("query") String query,
                                    @RequestParam("filters") @JsonProperty("filters") List<FilterDto> filters,
                                    @RequestParam("sortBy")SortingCriteria sortBy,
                                    @RequestParam("pageNumber") int pageNumber,
                                    @RequestParam("pageSize") int pageSize) {
        // search logic
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        Page<Product> productsPage = searchService.searchProducts(query, filters, sortBy, pageNumber, pageSize);
        searchResponseDto.setProductsPage(productsPage.map(GetProductDto::from));
        return searchResponseDto;
    }
}
