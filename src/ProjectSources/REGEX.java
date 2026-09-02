package ProjectSources;

import java.util.regex.Pattern;

public class REGEX {

    private static final String PHONE_REGEX = "^[6-9]\\d{9}$";

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{4,}$";

    private static final String NAME_REGEX = "^([A-Z][a-zA-Z]*)( [A-Z][a-zA-Z]*)*$";

    public static boolean is_phone_valid(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }

    public static boolean is_email_valid(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean is_password_valid(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean is_userName_valid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }
}
