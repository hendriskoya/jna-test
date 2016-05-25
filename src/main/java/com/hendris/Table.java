package com.hendris;

import com.hendris.winapi.Window;
import com.sun.jna.platform.win32.Guid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hendris on 5/17/16.
 */
public class Table implements Serializable {

    private String id;
    private String name;

    public Table(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "table [id: " + this.id + ", name: " + name + "]";
    }

    public static List<Table> fromWindows(List<Window> windows) {
        List<Table> tables = new ArrayList<>();

        for (Window window: windows) {
            Table table = new Table(window.getId(), window.getCaption());
            tables.add(table);
        }
        return tables;
    }
}
