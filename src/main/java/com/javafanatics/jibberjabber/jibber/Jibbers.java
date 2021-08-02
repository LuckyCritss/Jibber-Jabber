package com.javafanatics.jibberjabber.jibber;

import com.javafanatics.jibberjabber.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "jibbers")
public class Jibbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    @Column (name = "created_date")
    private Date createDate;

    @Column(length = 300)
    private String message;
}