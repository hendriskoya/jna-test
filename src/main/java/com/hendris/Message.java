package com.hendris;

/**
 * Created by hendris on 5/17/16.
 */
public class Message {

    private final long command;
    private final String payload;

    public Message(long command, String payload) {
        this.command = command;
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public long getCommand() {
        return command;
    }
}
