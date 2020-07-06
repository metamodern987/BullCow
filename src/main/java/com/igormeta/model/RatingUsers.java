package com.igormeta.model;

/**
 * Класс, описывающий рейтинг юзеров
 * согласно среднему числу попыток до победы (угадывания числа).
 *
 * Created by igor on 30.06.2020
 */

public class RatingUsers {

    private String userName;    //имя юзера
    private int countGames;     //число игр юзера
    private double averAttempts; //среднее число попыток до победы


    /*
    Геттеры и сеттеры для соответствующих полей класса RatingUsers
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCountGames() {
        return countGames;
    }

    public void setCountGames(int countGames) {
        this.countGames = countGames;
    }

    public double getAverAttempts() {
        return averAttempts;
    }

    public void setAverAttempts(double averAttempts) {
        this.averAttempts = averAttempts;
    }
}
