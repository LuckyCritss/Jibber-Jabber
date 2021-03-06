package com.javafanatics.jibberjabber.jibber;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JibberRepository extends JpaRepository<Jibber, Integer> {

    @Query("from Jibber t join fetch User u on t.user = u where u.handle = :handle order by t.createdDate desc")
    List<Jibber> findByHandle(String handle);

    @Query("from Jibber t join fetch User u on t.user = u where u.handle IN (:handles) order by t.createdDate desc")
    List<Jibber> findByHandle(@Param("handles") List<String> handle);

    @NotNull
    @Override
    @Query("from Jibber t join fetch User u on t.user = u order by t.createdDate desc")
    List<Jibber> findAll();

    @Query("from Jibber t join fetch User u on t.user = u where u.id = :id order by t.createdDate desc")
    List<Jibber> findByid(int id);
}
