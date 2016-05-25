package com.hendris.remote;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hendris.Table;

/**
 * Created by hendris on 5/17/16.
 */
public class Protocol {

    public static void main(String[] args) {
        Gson g = new Gson();

        /*String jsonTables = new Protocol().getJsonTables();
        System.out.println(jsonTables);*/

        Message requestMessage = new RequestMessage(MessageType.GET_TABLE_LIST);
        String jsonRequestMessage = g.toJson(requestMessage);
        System.out.println(jsonRequestMessage);

        Protocol protocol = new Protocol();
        String jsonResponseMessage = protocol.processMessage(jsonRequestMessage);

        ResponseMessage responseMessage = g.fromJson(jsonResponseMessage, ResponseMessage.class);

        System.out.println(responseMessage.getType());
        System.out.println(responseMessage.getBody());

        if (responseMessage.getType() == MessageType.GET_TABLE_LIST) {
            TableListBody tableListBody = g.fromJson(responseMessage.getBody(), TableListBody.class);
            for (Table table: tableListBody.getTables()) {
                System.out.println(table.toString());
            }
        }

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
            TableListBody tableListBody = new TableListBody(getTables());
            message = new ResponseMessage(MessageType.GET_TABLE_LIST, g.toJson(tableListBody));
            return g.toJson(message);
        } else {
            System.out.println("Tipo não implementado");
        }
        return "";
    }
}
