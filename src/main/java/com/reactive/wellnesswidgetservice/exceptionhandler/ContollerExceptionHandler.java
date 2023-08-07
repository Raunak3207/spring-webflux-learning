package com.reactive.wellnesswidgetservice.exceptionhandler;

import com.reactive.wellnesswidgetservice.dto.ErrorResponse;
import com.reactive.wellnesswidgetservice.exceptions.ResourceNotFoundException;
import com.reactive.wellnesswidgetservice.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ContollerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UserNotFoundException ex){
        ErrorResponse errorResponse =  new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String,String>> methodArgumentInvalidExceptionHandler(WebExchangeBindException ex){
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors  = result.getFieldErrors();
        Map<String,String> error_field_map  = new HashMap<>();
        for(FieldError err : fieldErrors){
            error_field_map.put(err.getField(),err.getDefaultMessage());
        }
        return new ResponseEntity<>(error_field_map,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorResponse> handleException(ServerWebInputException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourcenotFoundExceptionHandler(ResourceNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
