package com.soa.util;

import com.soa.enums.Mood;
import com.soa.enums.WeaponType;
import com.soa.exceptions.MyBadRequestException;
import com.soa.exceptions.ServiceFault;

public class FieldValidationUtil {


    public static Integer getIntegerFieldValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Cannot parse integer value from " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    public static Double getDoubleFieldValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Cannot parse double value from " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    public static Float getFloatFieldValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Cannot parse float value from " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    public static Long getLongFieldValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Cannot parse long value from " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    public static Boolean getBooleanFieldValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Cannot parse boolean value from " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    public static String getStringValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        return value;
    }

    public static Mood getMoodValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Mood.valueOf(value);
        } catch (IllegalArgumentException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Mood does not exist: " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    public static WeaponType getWeaponTypeValue(String value) throws MyBadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return WeaponType.valueOf(value);
        } catch (IllegalArgumentException e) {
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("WeaponType does not exist: " + value);
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    private static boolean isEmptyOrNull(String value) {
        if (value == null || value.equals("null"))
            return true;
        value = value.trim();
        return value.isEmpty();
    }

}