package com.igormeta.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Реализация основной логики игры.
 * Генерируем число из 4ёх цифр, где цифры не повторяются.
 *
 * Created by igor on 30.06.2020
 */

public class Game {

    private User user;                //игрок
    private List<GameMoves> gameMoves; //ходы
    private boolean guess;            //отгадал число или нет
    private String secretNum;         //загаданное число в виде строки

    /*
      Конструктор для класса Game и метод старта игры.
     */
    public Game() {
        startGame();
    }

    /*
    Метод стартует новую игру (инициализирует поля для Game) и загадывает число (secretNum).
     */
    public void startGame() {
        guess = false;
        Random rand = new Random();
        int rand1;
        gameMoves = new ArrayList<GameMoves>();
         while (!conformNum(rand1 = rand.nextInt(9900) + 100)); //т.к. может сгенерировать нуль
         secretNum = String.format("%04d", rand1);                    //загаданное число представили как строку в формате "0000"
    }

    /*
    Метод для проверки загаданного числа на необходимый формат для игры
    @param number - сгенерированное число
    @return true - проверка пройдена
    @return false - проверка  не пройдена (число не соответствует условиям)
     */
    public boolean conformNum(int number) {
        String checkStr = String.format("%04d", number);
        if (checkStr.length() == 4 && checkStr.matches("\\d{4}")) {
            return true;
        }
        return false;
    }

    /*
    Метод для возврата загаданного числа как строки
    @return secretNum
     */
    public String getSecretNum() {
        return secretNum;
    }

    /*
    Метод для проверки победы - отгадали число или нет
    @return true - число отгадано
    @return false - число не отгадано
     */
    public boolean checkGuess() {
        return guess;
    }

    /*
    Метод для отображение ходов текущей игры
    @return gameMoves - ходы игры
     */
    public List<GameMoves> getGameMoves() {
        return gameMoves;
    }

    /*
    Метод для возврата юзера
    @return user - юзер(текущий игрок)
     */
    public User getUser() {
        return user;
    }

    /*
    Метод для установки юзера
    @return user - юзер(текущий игрок)
     */
    public void setUser(User user) {
        this.user = user;
    }

    /*
    Метод для проверки введенного числа юзером на соответствие условиям
    и определяет "быка" и "корову".
    @param enterNum - число ,введенное юзером, в виде строки
     */
    public int verifyEnterNum(String enterNum) {
        int cow = 0;
        int bull = 0;
        if (enterNum.length() == 4 && enterNum.matches("\\d{4}")) {
            for (int i = 0; i < 4; i++ ) {
                if (enterNum.charAt(i) == secretNum.charAt(i)) {         //символ введенной строки равен символу строки в загаданном числе - бык
                    bull++;
                }
                else
                    if (secretNum.contains(String.valueOf(enterNum.charAt(i)))) { //содержит один из символов - корова
                        cow++;
                    }
            }
            if (bull == 4) {
               guess = true; //отгадано число
            }
            ///GameMoves gameMoves = new GameMoves();
            GameMoves currentMoves = new GameMoves();   //текущий ход игры
            currentMoves.setEnterNumber(enterNum);
            currentMoves.setBull(String.valueOf(bull)); //преобразуем "быка" из числа в строку
            currentMoves.setCow(String.valueOf(cow));   //преобразуем "корову" из числа в строку
            gameMoves.add(currentMoves);                //добавляем текущий ход в основной лист ходов. Будем показывать юзеру
        }
        return bull;
    }
}
