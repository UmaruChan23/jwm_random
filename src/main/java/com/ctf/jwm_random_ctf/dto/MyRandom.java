package com.ctf.jwm_random_ctf.dto;

import lombok.Data;

import java.util.Random;

@Data
public class MyRandom {

    private Random random1;
    private Random random2;
    private Random random3;
    private Random random4;

    public MyRandom() {
        random1 = new Random();
        random2 = new Random();
        random3 = new Random();
        random4 = new Random();
    }

    public long nextNumber() {
        String number1 = String.valueOf(random1.nextLong(0, 10));
        String number2 = String.valueOf(random2.nextLong(0, 10));
        String number3 = String.valueOf(random3.nextLong(0, 10));
        String number4 = String.valueOf(random4.nextLong(0, 10));
        return Long.parseLong(number1 + number2 + number3 + number4);
    }

}
