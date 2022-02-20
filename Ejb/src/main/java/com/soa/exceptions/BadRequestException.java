package com.soa.exceptions;

import java.io.Serializable;

public class BadRequestException extends Exception implements Serializable {
    public BadRequestException(String message){
        super(message);
    }
}
