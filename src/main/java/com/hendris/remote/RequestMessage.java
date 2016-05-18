package com.hendris.remote;

/**
 * Created by hendris on 5/18/16.
 */
public class RequestMessage extends Message<String> {

    public RequestMessage(int type, String body) {
        super(type, body);
    }
}
