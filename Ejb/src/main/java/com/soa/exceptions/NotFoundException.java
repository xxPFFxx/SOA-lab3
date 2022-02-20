package com.soa.exceptions;

import java.io.Serializable;

public class NotFoundException extends Exception implements Serializable {
    public NotFoundException(String message){
        super(message);
    }
}
