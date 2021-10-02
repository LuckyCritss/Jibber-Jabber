package com.javafanatics.jibberjabber.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiUserObject {

    public ApiUserObject(int id, String email, String handle, List<String> followers, List<String> following) {
        this.id = id;
        this.email = email;
        this.handle = handle;
        this.followers = followers;
        this.following = following;
    }

    public ApiUserObject() {

    }

    private int id;

    private String email;

    private String handle;

    private List<String> followers;

    private List<String> following;

}
