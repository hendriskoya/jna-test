package com.hendris.remote;

import com.hendris.winapi.WinAPI;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hendris.Table;

/**
 * Created by hendris on 5/17/16.
 */
public class Engine {

    private static Gson g = new Gson();
    private static WinAPI wApi = new WinAPI();


    public String processMessage(String request) {
        System.out.println("request: " + request);
        Message message = g.fromJson(request, RequestMessage.class);
        if (message.getType() == MessageType.GET_TABLE_LIST) {
            List<Table> tables = Table.fromWindows(wApi.loadWindows());
            TableListBody tableListBody = new TableListBody(tables);
            message = new ResponseMessage(MessageType.GET_TABLE_LIST, g.toJson(tableListBody));
            return g.toJson(message);
        } else {
            System.out.println("Tipo não implementado");
        }
        return "";
    }
}
