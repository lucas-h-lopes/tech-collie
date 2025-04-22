package com.fatec.techcollie.service.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String msg){
        super(msg);
    }
}
