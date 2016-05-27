package com.hendris.remote;

/**
 * Created by Hendris on 5/27/2016.
 */
public class ExecutedBody {

    private final boolean success;

    public ExecutedBody(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
