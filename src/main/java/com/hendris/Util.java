package com.hendris;

import java.util.Random;

/**
 * Created by hendris on 5/25/16.
 */
public class Util {

    public static String generateId() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            sb.append(Integer.toString(Math.abs(random.nextInt()) % 16, 16));
        }
        return sb.toString();
    }
}
