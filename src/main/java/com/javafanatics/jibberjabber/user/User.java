package com.javafanatics.jibberjabber.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
    @Getter
    @Setter
    @Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{user.email}")
    @Email(message = "{user.email}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{user.handle}")
    @Size(min = 5, message = "{user.handle}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$")
    private String handle;

    @Column(name = "password")
    private String passWord;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_relations",
            joinColumns={@JoinColumn(name="ParentId")},
            inverseJoinColumns={@JoinColumn(name="UserId")})
    private Set<User> followers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_relations",
            joinColumns={@JoinColumn(name="UserId")},
            inverseJoinColumns={@JoinColumn(name="ParentId")})
    private Set<User> following;

    @Transient
    private String CheckPassWord;

    // Create md5 hash from given email for gravatar
    public String getUserEmailMD5() {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(this.email.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
