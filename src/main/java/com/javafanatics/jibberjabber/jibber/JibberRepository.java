package com.javafanatics.jibberjabber.jibber;

import com.javafanatics.jibberjabber.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JibberRepository extends JpaRepository<Jibbers, Integer> {


    @Override
    @Query("SELECT t from Jibbers t")
    List<Jibbers>findAll();

    default User getUserById(int userId) {
        return getUserById(userId);
    }

    default void saveAll(Jibbers jibberTweet) {
    }
}