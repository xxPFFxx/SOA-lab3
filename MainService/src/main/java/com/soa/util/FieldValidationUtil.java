package com.soa.util;

import com.soa.enums.Mood;
import com.soa.enums.WeaponType;
import com.soa.exceptions.BadRequestException;

public class FieldValidationUtil {

    public static Integer getIntegerFieldValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Cannot parse integer value from " + value);
        }
    }

    public static Double getDoubleFieldValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Cannot parse double value from " + value);
        }
    }

    public static Float getFloatFieldValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Cannot parse float value from " + value);
        }
    }

    public static Long getLongFieldValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Cannot parse long value from " + value);
        }
    }

    public static Boolean getBooleanFieldValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Cannot parse boolean value from " + value);
        }
    }

    public static String getStringValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        return value;
    }

    public static Mood getMoodValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Mood.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Mood does not exist: " + value);
        }
    }

    public static WeaponType getWeaponTypeValue(String value) throws BadRequestException {
        if (isEmptyOrNull(value))
            return null;
        try {
            return WeaponType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("WeaponType does not exist: " + value);
        }
    }

    private static boolean isEmptyOrNull(String value) {
        if (value == null || value.equals("null"))
            return true;
        value = value.trim();
        return value.isEmpty();
    }

}