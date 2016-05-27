package com.hendris.winapi;

import com.hendris.Util;
import com.sun.jna.platform.win32.WinDef.HWND;
import java.util.Random;

public class Window {

    private final String id;
    private final String caption;
    private final HWND hWnd;
    private final int[] rect;

    public Window(String caption, HWND hWnd, int[] rect) {
        this.id = Util.generateId();
        this.caption = caption;
        this.hWnd = hWnd;
        this.rect = rect;
    }

    public String getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public HWND getHandle() {
        return hWnd;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("id: ");
        sb.append(id);
        sb.append(", caption: ");
        sb.append(caption);
        sb.append(", rect: ");
        sb.append(String.format("%d %d %d %d", rect[0], rect[1], rect[2], rect[3]));
        return sb.toString();
    }
}