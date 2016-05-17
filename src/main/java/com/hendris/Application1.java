package com.hendris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;

public class Application1 {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Toolkit.getDefaultToolkit().addAWTEventListener(
          new Listener(), AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Robot r = new Robot();
        r.mouseMove(1258,9);
        r.mousePress( InputEvent.BUTTON1_MASK );
        r.mouseRelease( InputEvent.BUTTON1_MASK );
//        Thread.sleep(50);
        /*r.mousePress( InputEvent.BUTTON1_MASK );
        r.mouseRelease( InputEvent.BUTTON1_MASK );*/

    }

    private static class Listener implements AWTEventListener {
        public void eventDispatched(AWTEvent event) {
            System.out.print(MouseInfo.getPointerInfo().getLocation() + " | ");
            System.out.println(event);
        }
    }
}
