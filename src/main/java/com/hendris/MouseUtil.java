package com.hendris;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by hendris on 6/7/16.
 */
public class MouseUtil {

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void clickAtPosition(int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
