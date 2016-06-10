package com.hendris;

import com.hendris.winapi.WinAPI;

/**
 * Created by hendris on 6/8/16.
 */
public class TesteChild {

    public static void main(String[] args) {
        WinAPI instance = WinAPI.INSTANCE;
        String windowCaption = "Calculator";
        if (args != null && args.length > 0) {
            System.out.println("arg[0]: " + args[0]);
            windowCaption = args[0];
        }
        instance.teste(windowCaption);
    }
}
