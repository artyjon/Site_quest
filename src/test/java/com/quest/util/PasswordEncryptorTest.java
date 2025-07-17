package com.quest.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptorTest {

    @Test
    void givenSamePassword_whenHashed_thenHashesShouldBeEqual() {
        String password = "mySecret123";

        String hash1 = PasswordEncryptor.hashPassword(password);
        String hash2 = PasswordEncryptor.hashPassword(password);

        assertEquals(hash1, hash2, "Хеши одного и того же пароля должны совпадать");
    }

    @Test
    void givenCorrectPassword_whenCheckPassword_thenReturnTrue() {
        String password = "superSecure!";
        String hashed = PasswordEncryptor.hashPassword(password);

        boolean result = PasswordEncryptor.checkPassword("superSecure!", hashed);

        assertTrue(result, "Проверка пароля должна вернуть true для корректного пароля");
    }

    @Test
    void givenIncorrectPassword_whenCheckPassword_thenReturnFalse() {
        String password = "correctHorseBatteryStaple";
        String hashed = PasswordEncryptor.hashPassword(password);

        boolean result = PasswordEncryptor.checkPassword("wrongPassword", hashed);

        assertFalse(result, "Проверка пароля должна вернуть false для неправильного пароля");
    }

    @Test
    void givenEmptyPassword_whenHashed_thenHashNotNullOrEmpty() {
        String password = "";

        String hash = PasswordEncryptor.hashPassword(password);

        assertNotNull(hash, "Хеш не должен быть null");
        assertFalse(hash.isEmpty(), "Хеш не должен быть пустым");
    }
}