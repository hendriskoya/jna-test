package com.hendris.remote;

/**
 * Created by hendris on 5/17/16.
 */
public abstract class Message<T> {

    private final int type;
    private final T body;

    public Message(int type, T body) {
        this.type = type;
        this.body = body;
    }

    public T getBody() {
        return body;
    }

    public int getType() {
        return type;
    }
}
