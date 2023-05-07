package com.sysmap.showus.services.validators;

import java.util.regex.Pattern;

public class Validator{
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 3;
    }
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        String regex = "\\S{3,}";
        return password.matches(regex);
    }
    public static boolean isValidTitleDescription(String title, String description) {
        return title != null && title.trim().length() >= 3 && description != null && description.trim().length() >= 3;
    }
}
