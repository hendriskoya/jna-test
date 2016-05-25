package com.hendris.remote;

import com.hendris.Table;
import java.util.List;

/**
 * Created by hendris on 5/18/16.
 */
public class TableListBody  {

    private final List<Table> tables;

    public TableListBody(List<Table> tables) {
        this.tables = tables;
    }

    public List<Table> getTables() {
        return tables;
    }
}
