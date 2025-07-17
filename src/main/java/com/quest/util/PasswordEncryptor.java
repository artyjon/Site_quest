package com.quest.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordEncryptor {

    private PasswordEncryptor() {}

    // Метод для хеширования пароля с использованием SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not supported", e);
        }
    }

    // Проверка пароля
    public static boolean checkPassword(String originalPassword, String storedHash) {
        String hashedPassword = hashPassword(originalPassword);
        return hashedPassword.equals(storedHash);
    }
}
