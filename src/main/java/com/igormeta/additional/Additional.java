package com.igormeta.additional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**Класс с дополнительным методом, возвращающим зашифрованное значение пароля юзера
 * в виде строки.
 * Created by igor on 02.07.2020
 */

public class Additional {

    /*
    Метод возвращает значение на основе алгоритма MD5 и инстанса класса MessageDigest (дайджест сообщения).
    @param enteredStr - строка с начальным паролем
    @return hashPass -  MD5 строка
     */

    public static String getHashPass(String enteredStr) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(enteredStr.getBytes("UTF-8"));
        byte[] digest = messageDigest.digest();
        BigInteger bigInteger = new BigInteger(1, digest);
        String hashPass = bigInteger.toString(16);

        //if ()
        while (hashPass.length() < 32) {
            hashPass = "0" + hashPass;
        }
        return hashPass;
    }
}
