package com.quest.util;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static boolean isFieldEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) return true;
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*\\p{L}.*") && password.matches(".*\\d.*");
    }
}
