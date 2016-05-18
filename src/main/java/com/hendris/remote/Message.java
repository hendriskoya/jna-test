package com.hendris.remote;

/**
 * Created by hendris on 5/17/16.
 */
public class Message {

    public enum Type {
        GET_TABLE_LIST;
    }

    private final Type type;
    private final String body;

    public Message(Type type, String body) {
        this.type = type;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public Type getType() {
        return type;
    }
}
