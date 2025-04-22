package com.fatec.techcollie.web.exception;

import com.fatec.techcollie.service.exception.BadRequestException;
import com.fatec.techcollie.service.exception.InternalServerErrorException;
import com.fatec.techcollie.service.exception.NotFoundException;
import com.fatec.techcollie.service.exception.UniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandler {

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ExceptionBody> uniqueViolationException(HttpServletRequest request, UniqueViolationException e){
        log(e);
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.CONFLICT, e.getMessage(), e, false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionBody);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionBody> badRequestException(HttpServletRequest request, Exception e){
        log(e);
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.BAD_REQUEST, e.getMessage(), e, false);
        return ResponseEntity.badRequest().body(exceptionBody);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionBody> notFoundException(NotFoundException e, HttpServletRequest request){
        log(e);
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.NOT_FOUND, e.getMessage(), e ,false);
        return ResponseEntity.status(404).body(exceptionBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> validationException(MethodArgumentNotValidException e, HttpServletRequest request){
        log(e);
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.UNPROCESSABLE_ENTITY, "Validation error(s)", e, true);
        return ResponseEntity.status(422).body(exceptionBody);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ExceptionBody> internalServerError(Exception e, HttpServletRequest request){
        log(e);
        ExceptionBody exceptionBody = new ExceptionBody(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e ,false);
        return ResponseEntity.status(500).body(exceptionBody);
    }

    private void log(Exception e){
        e.printStackTrace();
        log.info("Exception {} message: {}", e.getClass().getName(), e.getMessage());
    }
}
