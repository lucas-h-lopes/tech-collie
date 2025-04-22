package com.fatec.techcollie.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonPropertyOrder({"path", "method", "status", "statusMessage", "message", "errors"})
public class ExceptionBody {

    private String path;
    private String method;
    private Integer status;
    private String statusMessage;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;


    public ExceptionBody(HttpServletRequest request, HttpStatus status, String message, Exception exception, boolean isValidationError) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;

        if (isValidationError) {
            this.errors = getFieldErrors(exception);
        }
    }

    private Map<String, String> getFieldErrors(Exception e) {
        HashMap<String, String> result = new HashMap<>();
        if (e instanceof MethodArgumentNotValidException validationE) {
            for (FieldError error : validationE.getFieldErrors()) {
                result.put(error.getField(), error.getDefaultMessage());
            }
            return result;
        }
        return null;
    }
}
