package com.hendris;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * Created by hendris on 5/17/16.
 */
public class CommProtocol {

    public static void main(String[] args) {
        String jsonTables = new CommProtocol().getJsonTables();
        System.out.println(jsonTables);
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
}
