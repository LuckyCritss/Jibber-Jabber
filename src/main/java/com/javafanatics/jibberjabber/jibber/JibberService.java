package com.javafanatics.jibberjabber.jibber;

import com.javafanatics.jibberjabber.user.User;

import java.util.List;

public interface JibberService {

    void save(Jibber jibber);

    List<Jibber> findByHandle(String handle);

    List<Jibber> findByid(int id);

    List<Jibber> findAll();

    List<Jibber> getFeedForUser(User user);

}
