package com.javafanatics.jibberjabber.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByHandle(String handle);

    @Query("from User u " +
            "left join fetch u.followers " +
            "left join fetch u.following " +
            "where u.handle = :handle")
    User getUserComplete(String handle);

    @Query("select distinct User from User")
    User getAll();
}
