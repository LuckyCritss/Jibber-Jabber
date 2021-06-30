package com.javafanatics.jibberjabber.tweet;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name="tweet")
public class tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date")
    private Date date;

    private String message;


}
