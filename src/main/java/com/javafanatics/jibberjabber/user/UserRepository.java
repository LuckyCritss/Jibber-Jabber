package com.javafanatics.jibberjabber.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u Where u.handle = :handle")
    public User getUserByHandle(@Param("handle") String handle);

    @Query("SELECT u from User u Where u.email = :email")
    public User getUserByEmail (@Param("email") String email);

    @Override
    @Query("SELECT u from User u")
    List<User> findAll();

    User getUserById (Integer integer);

}