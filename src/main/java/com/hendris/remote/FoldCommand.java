package com.hendris.remote;

import com.hendris.Table;

/**
 * Created by hendris on 6/7/16.
 */
public class FoldCommand {

    private Table table;

    public FoldCommand(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
