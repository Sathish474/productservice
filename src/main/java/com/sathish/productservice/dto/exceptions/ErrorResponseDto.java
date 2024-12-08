package com.sathish.productservice.dto.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
    private String message;
    private String status;
}
