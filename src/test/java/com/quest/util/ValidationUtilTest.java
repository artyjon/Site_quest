package com.quest.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void givenFieldsWithNull_whenCheckIsFieldEmpty_thenReturnTrue() {
        String[] fields = {"login", null, "password"};
        boolean result = ValidationUtil.isFieldEmpty(fields);
        assertTrue(result);
    }

    @Test
    void givenFieldsWithOnlyWhitespace_whenCheckIsFieldEmpty_thenReturnTrue() {
        String[] fields = {"   ", "\t", "\n"};
        boolean result = ValidationUtil.isFieldEmpty(fields);
        assertTrue(result);
    }

    @Test
    void givenAllNonEmptyFields_whenCheckIsFieldEmpty_thenReturnFalse() {
        String[] fields = {"login", "password", "name"};
        boolean result = ValidationUtil.isFieldEmpty(fields);
        assertFalse(result);
    }

    @Test
    void givenPasswordWithLettersAndDigits_whenCheckIsPasswordValid_thenReturnTrue() {
        String password = "Pass1234";
        boolean result = ValidationUtil.isPasswordValid(password);
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ab1", "Password", "12345678", "\n"})
    void givenIncorrectPassword_whenCheckIsPasswordValid_thenReturnFalse(String password) {
        boolean result = ValidationUtil.isPasswordValid(password);
        assertFalse(result);
    }
}