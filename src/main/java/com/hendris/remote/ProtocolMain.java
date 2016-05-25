package com.hendris.remote;

import com.google.gson.Gson;

import com.hendris.Table;

/**
 * Created by hendris on 5/17/16.
 */
public class ProtocolMain {

    public static void main(String[] args) {
        Gson g = new Gson();

        /*String jsonTables = new Engine().getJsonTables();
        System.out.println(jsonTables);*/

        Message requestMessage = new RequestMessage(MessageType.GET_TABLE_LIST);
        String jsonRequestMessage = g.toJson(requestMessage);
        System.out.println(jsonRequestMessage);

        Engine protocol = new Engine();
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

    /*    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        Table table = new Table("1000", "WinAPI 1");
        tables.add(table);

        table = new Table("2000", "WinAPI 2");
        tables.add(table);

        return tables;
    }

    public String getJsonTables() {
        Gson g = new Gson();
        return g.toJson(getTables());
    }*/


}
