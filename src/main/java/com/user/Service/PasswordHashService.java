package com.user.Service;

import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class PasswordHashService {

    // Generate a secure password hash
    public String hashPassword(String password) {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException(
                    "Password cannot be empty"
            );
        }

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(12)
        );
    }

    // Verify a password during login
    public boolean verifyPassword(
            String password,
            String passwordHash) {

        if (password == null || passwordHash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(password, passwordHash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
