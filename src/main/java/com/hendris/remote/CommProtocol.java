package com.hendris.remote;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hendris.Table;

/**
 * Created by hendris on 5/17/16.
 */
public class CommProtocol {

    public static void main(String[] args) {
        String jsonTables = new CommProtocol().getJsonTables();
        System.out.println(jsonTables);

        Gson g = new Gson();
        Message requestMessage = new RequestMessage(MessageType.GET_TABLE_LIST, "");
        String jsonMessage = g.toJson(requestMessage);
        System.out.println(jsonMessage);

        String responseMessage = new CommProtocol().processMessage(jsonMessage);
        System.out.println(responseMessage);

        Message testeMessage = new RequestMessage(MessageType.GET_TABLE_LIST, jsonTables);
        String s = g.toJson(testeMessage);
        System.out.println(s);
        RequestMessage requestMessage1 = g.fromJson(s, RequestMessage.class);
        System.out.println(requestMessage1.getType());
        System.out.println(requestMessage1.getBody());

        List<Table> teste = new ArrayList<>();

        List list = g.fromJson(requestMessage1.getBody(), teste.getClass());
        System.out.println(list);

        /*Message message1 = g.fromJson(jsonMessage, Message.class);
        System.out.println(requestMessage.getType());
        System.out.println(requestMessage.getBody());*/
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        Table table = new Table(1000, "Teste 1");
        tables.add(table);

        table = new Table(2000, "Teste 2");
        tables.add(table);

        return tables;
    }

    public String getJsonTables() {
        Gson g = new Gson();
        return g.toJson(getTables());
    }

    private static Gson g = new Gson();

    public String processMessage(String request) {
        System.out.println("request: " + request);
        Message message = g.fromJson(request, RequestMessage.class);
        if (message.getType() == MessageType.GET_TABLE_LIST) {
            message = new TableListMessage(MessageType.GET_TABLE_LIST, getTables());
            return g.toJson(message);
        } else {
            System.out.println("Tipo não implementado");
        }
        return "";
    }
}
