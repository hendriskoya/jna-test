package com.hendris.remote;

/**
 * Created by hendris on 5/18/16.
 */
public class ResponseMessage extends Message<String> {

    public ResponseMessage(int type, String body) {
        super(type, body);
    }
}
