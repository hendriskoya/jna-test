package com.hendris.winapi;

/**
 * Created by hendris on 6/7/16.
 */
public class Position {

    private final int[] rect;

    public Position(int[] rect) {
        this.rect = rect;
    }

    public int[] getRect() {
        return rect;
    }
}
