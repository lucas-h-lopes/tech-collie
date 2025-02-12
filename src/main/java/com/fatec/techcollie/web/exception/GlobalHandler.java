package com.fatec.techcollie.web.exception;

import com.fatec.techcollie.service.exception.UniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ExceptionBody> uniqueViolationException(HttpServletRequest request, UniqueViolationException e){
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.CONFLICT, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> validationException(MethodArgumentNotValidException e, HttpServletRequest request, BindingResult result){
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.UNPROCESSABLE_ENTITY, "Validation error(s)", e, result);
        return ResponseEntity.status(422).body(exceptionBody);
    }
}
