package com.javafanatics.jibberjabber.user;

import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface UserService {

    class PasswordMisMatchException extends Exception {
        public PasswordMisMatchException(String message) {
            super(message);
        }
    }

    List<User> getAll();

    public void save(User user) throws PasswordMisMatchException;

}
