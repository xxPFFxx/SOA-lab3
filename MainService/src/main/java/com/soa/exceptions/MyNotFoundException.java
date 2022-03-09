package com.soa.exceptions;

import java.io.Serializable;

public class MyNotFoundException extends Exception implements Serializable {
    private static final long serialVersionUID = 20L;
    private ServiceFault fault;
    public MyNotFoundException(String message) {
        super(message);
    }
    public MyNotFoundException(Throwable throwable) {
        super(throwable);
    }
    public MyNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public MyNotFoundException() {
    }
    public MyNotFoundException(ServiceFault fault) {
        super(fault.getFaultString());
        this.fault = fault;
    }
    public MyNotFoundException(String message, ServiceFault faultInfo) {
        super(message);
        this.fault = faultInfo;
    }
    public MyNotFoundException(String message, ServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.fault = faultInfo;
    }
    public ServiceFault getFaultInfo() {
        return fault;
    }
    public MyNotFoundException(String code, String message) {
        super(message);
        this.fault = new ServiceFault();
        this.fault.setFaultString(message);
        this.fault.setFaultCode(code);
    }
}
