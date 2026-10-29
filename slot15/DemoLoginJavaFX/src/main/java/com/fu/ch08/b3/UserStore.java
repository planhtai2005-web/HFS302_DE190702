
package com.fu.ch08.b3;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class UserStore {

    private static final Map<String, User> USERS = new HashMap<>();

    static {
        add(new User(
                "Quan tri vien",
                "admin@example.com",
                "admin",
                "Admin@123",
                "Khác",
                LocalDate.of(2000, 1, 1),
                "Kỹ thuật phần mềm",
                List.of()
        ));
    }

    private UserStore() {
    }

    private static String normalize(String username) {
        return username == null
                ? ""
                : username.trim().toLowerCase(Locale.ROOT);
    }

    public static boolean exists(String username) {
        return USERS.containsKey(normalize(username));
    }

    public static void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User không được null.");
        }

        String key = normalize(user.username());

        if (key.isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống.");
        }

        if (USERS.containsKey(key)) {
            throw new IllegalArgumentException("Username đã tồn tại.");
        }

        USERS.put(key, user);
    }

    public static Optional<User> login(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }

        User user = USERS.get(normalize(username));

        if (user != null && user.password().equals(password)) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
