package com.companyname.demo.errors;

import com.companyname.demo.errors.dto.ErrorDTO;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice //-> this class becomes a centralized component for handling Exceptions
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override // this method handles exceptions made by validation annotations
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request
    ) {
        //we need to convert a list of errors to a dto list
        // because we cant serialize field errors back in JSON!
        return new ResponseEntity<>(createErrors(ex.getFieldErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class) //ex is exception that is being thrown
    public ResponseEntity<Object> handleCustomValidationException(ValidationException ex) {
        return new ResponseEntity<>(createErrors(ex.getErrors().getFieldErrors()), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createErrors(List<FieldError> fieldErrorList) {
        List<ErrorDTO> errors = fieldErrorList.stream()
                .map(fieldError -> new ErrorDTO(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Validation failed!");
        errorResponse.put("errors", errors); //list of errors
        return errorResponse;
    }
}