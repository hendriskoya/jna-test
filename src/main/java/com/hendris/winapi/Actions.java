package com.hendris.winapi;

/**
 * Created by hendris on 6/7/16.
 */
public class Actions {

    private Fold fold;
    private Check check;
    private Raise raise;

    public Actions(Fold fold, Check check, Raise raise) {
        this.fold = fold;
        this.check = check;
        this.raise = raise;
    }
}
