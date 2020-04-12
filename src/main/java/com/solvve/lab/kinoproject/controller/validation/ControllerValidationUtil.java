package com.solvve.lab.kinoproject.controller.validation;


import com.solvve.lab.kinoproject.exception.ControllerValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerValidationUtil {

    public static <T extends Comparable<T>> void validateLessThen(T value1, T value2,
                                                                  String fieldName, String field2Name) {
        if (value1.compareTo(value2) >= 0) {
            throw new ControllerValidationException(String.format(" Field %s=%s should be less then %s=%s",
                    fieldName, value1, field2Name, value2));
        }
    }
}
