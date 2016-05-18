package com.hendris.remote;

import com.hendris.Table;
import java.util.List;

/**
 * Created by hendris on 5/18/16.
 */
public class TableListMessage extends Message<List<Table>> {

    public TableListMessage(int type, List<Table> body) {
        super(type, body);
    }
}
