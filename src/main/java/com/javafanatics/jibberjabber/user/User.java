package com.javafanatics.jibberjabber.user;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email(message = "{user.email}")
    private String email;

    private String handle;

    @Size(min = 5, message = "{user.password}")
    private String password;

    private boolean enabled;

    @Column(name = "role")
    private String role;



}
