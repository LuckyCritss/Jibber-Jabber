package com.javafanatics.jibberjabber.api;
import com.javafanatics.jibberjabber.jibber.Jibber;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiJibberObject {

    public ApiJibberObject(int id, String message, Date createdDate, String user) {
        this.id = id;
        this.message = message;
        this.createdDate = createdDate;
        this.user = user;
    }

    public ApiJibberObject() {
    }

    private int id;

    private String message;

    private Date createdDate;

    String user;

}
