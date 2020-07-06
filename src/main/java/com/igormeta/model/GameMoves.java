package com.igormeta.model;

/**
 * Класс, описывающий состояние ходов игры.
 *
 * Created by igor on 30.06.2020
 */

public class GameMoves {

    private String bull;          //бык - точно угаданные цифры (в виде строки)
    private String cow;           //корова - угаданные цифры без учета позиции (в виде строки)
    private String enterNumber;   //введенная строка юзером


    /*
    Геттеры и сеттеры для соответствующих полей класса GameMoves
     */
    public String getBull() {
        return bull;
    }

    public void setBull(String bull) {
        this.bull = bull;
    }

    public String getCow() {
        return cow;
    }

    public void setCow(String cow) {
        this.cow = cow;
    }

    public String getEnterNumber() {
        return enterNumber;
    }

    public void setEnterNumber(String enterNumber) {
        this.enterNumber = enterNumber;
    }

}
