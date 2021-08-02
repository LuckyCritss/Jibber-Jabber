package com.javafanatics.jibberjabber.jibber;

import java.util.List;

public interface JibberService {

    List<Jibbers> getAll();

    void saveOrUpdate(Jibbers jibberTweet, int userId);
}