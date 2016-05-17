package com.hendris;

import java.io.Serializable;

/**
 * Created by hendris on 5/17/16.
 */
public class Table implements Serializable {

    private long id;
    private String name;

    public Table(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
