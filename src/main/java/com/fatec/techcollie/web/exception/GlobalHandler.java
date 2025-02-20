package com.fatec.techcollie.web.exception;

import com.fatec.techcollie.service.exception.UniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ExceptionBody> uniqueViolationException(HttpServletRequest request, UniqueViolationException e){
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.CONFLICT, e.getMessage(), e, false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionBody);
    }

    @ExceptionHandler()
    public ResponseEntity<ExceptionBody> badRequestException(HttpServletRequest request, Exception e){
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.BAD_REQUEST, e.getMessage(), e, false);
        return ResponseEntity.badRequest().body(exceptionBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> validationException(MethodArgumentNotValidException e, HttpServletRequest request){
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.UNPROCESSABLE_ENTITY, "Validation error(s)", e, true);
        return ResponseEntity.status(422).body(exceptionBody);
    }
}
