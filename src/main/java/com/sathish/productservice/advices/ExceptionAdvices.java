package com.sathish.productservice.advices;

import com.sathish.productservice.dto.exceptions.ErrorResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice
@RestControllerAdvice
public class ExceptionAdvices {
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleExceptions(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatus("ERROR");
        return errorResponseDto;
    }
}
