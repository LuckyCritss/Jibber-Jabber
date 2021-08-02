package com.javafanatics.jibberjabber.user;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{user.email1}")
    @Email(message = "{user.email}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{user.handle}")
    @Size(min = 5, message = "{user.handle}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$")
    private String handle;

    @Size(min = 5, message = "{user.password}")
    @Column(name = "password")
    private String passWord;

    @Transient
    private String CheckPassWord;

}