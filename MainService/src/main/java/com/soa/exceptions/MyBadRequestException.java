package com.soa.exceptions;

import java.io.Serializable;

public class MyBadRequestException extends Exception implements Serializable {
    private static final long serialVersionUID = 10L;
    private ServiceFault fault;
    public MyBadRequestException(String message) {
        super(message);
    }
    public MyBadRequestException(Throwable throwable) {
        super(throwable);
    }
    public MyBadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public MyBadRequestException() {
    }
    public MyBadRequestException(ServiceFault fault) {
        super(fault.getFaultString());
        this.fault = fault;
    }
    public MyBadRequestException(String message, ServiceFault faultInfo) {
        super(message);
        this.fault = faultInfo;
    }
    public MyBadRequestException(String message, ServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.fault = faultInfo;
    }
    public ServiceFault getFaultInfo() {
        return fault;
    }
    public MyBadRequestException(String code, String message) {
        super(message);
        this.fault = new ServiceFault();
        this.fault.setFaultString(message);
        this.fault.setFaultCode(code);
    }
}
