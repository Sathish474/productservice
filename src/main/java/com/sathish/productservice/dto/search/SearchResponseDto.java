package com.sathish.productservice.dto.search;

import com.sathish.productservice.dto.products.GetProductDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class SearchResponseDto {
    private Page<GetProductDto> productsPage;
}
