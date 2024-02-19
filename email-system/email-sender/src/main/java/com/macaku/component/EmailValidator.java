package com.macaku.component;

public class EmailValidator {

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static boolean isEmailAccessible(String email) {
        return email.matches(EMAIL_PATTERN);
    }

}