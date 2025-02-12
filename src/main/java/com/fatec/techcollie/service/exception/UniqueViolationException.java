package com.fatec.techcollie.service.exception;

public class UniqueViolationException extends RuntimeException{
    public UniqueViolationException(String msg){
        super(msg);
    }
}
