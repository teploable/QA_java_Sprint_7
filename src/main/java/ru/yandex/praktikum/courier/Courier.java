package ru.yandex.praktikum.courier;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Courier {
    private String login;
    private String password;
    private String firstName;
    private String id;

    public Courier(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }
}
