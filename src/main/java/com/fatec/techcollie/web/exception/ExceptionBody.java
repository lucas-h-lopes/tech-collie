package com.fatec.techcollie.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.TreeMap;

@Getter @Setter
@JsonPropertyOrder({"path", "method", "status", "statusMessage", "message", "errors","exception"})
public class ExceptionBody {

    private String path;
    private String method;
    private Integer status;
    private String statusMessage;
    private String message;
    private String exception;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ExceptionBody(HttpServletRequest request, HttpStatus status, String message, Exception exception){
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
        this.exception = exception.getClass().getName();
    }

    public ExceptionBody(HttpServletRequest request, HttpStatus status, String message, Exception exception, BindingResult result){
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
        this.exception = exception.getClass().getName();
        this.errors = new TreeMap<>();
        addErrors(errors, result);
    }

    private void addErrors(Map<String,String> errors, BindingResult result){
        for(FieldError error : result.getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
    }
}
