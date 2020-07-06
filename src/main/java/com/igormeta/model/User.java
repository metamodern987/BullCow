package com.igormeta.model;

/**
 * Класс, описывающий юзера (игрока).
 * Created by igor on 30.06.2020
 */

public class User {

    private Long id;             //уникальный идентификатор
    private String fullName;     //имя
    private String userName;     //имя юзера
    private String userPassword; //пароль

    /*
    Геттеры и сеттеры для соответствующих полей класса User.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
