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

    User findUser(String needle);

    List<User> getAll();

    void save(User user) throws PasswordMisMatchException;

}
