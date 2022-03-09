package com.soa.exceptions;

public class ServiceFault {
    private String faultCode;
    private String faultString;
    public String getFaultCode() {
        return faultCode;
    }
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
    public String getFaultString() {
        return faultString;
    }
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}