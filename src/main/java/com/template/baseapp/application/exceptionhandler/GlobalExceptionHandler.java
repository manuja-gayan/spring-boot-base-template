package com.template.baseapp.application.exceptionhandler;

import com.template.baseapp.domain.exception.ProductCustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ProductCustomException.class})
    public ResponseEntity<Object> genericException(RuntimeException exception) {
        Map<String,Object> response = createResponse(exception);
        return ResponseEntity.status((int)response.get("code")).body(response);
    }
    private Map<String,Object> createResponse(RuntimeException exception){
        Map<String,Object> response = new HashMap<>();
        if(exception instanceof ProductCustomException){
            ProductCustomException pe = (ProductCustomException) exception;
            response.put("code", 400);
            response.put("message",pe.getMessage());
        }
        return response;
    }
}
