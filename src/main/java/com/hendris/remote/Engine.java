package com.hendris.remote;

import com.hendris.winapi.WinAPI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.hendris.Table;
import com.hendris.winapi.Window;

/**
 * Created by hendris on 5/17/16.
 */
public class Engine {

    private static Gson g = new Gson();
    private static WinAPI wApi = WinAPI.INSTANCE;

    public String processMessage(String request) {
        System.out.println("request: " + request);
        RequestMessage message = g.fromJson(request, RequestMessage.class);
        if (message.getType() == MessageType.GET_TABLE_LIST) {
            List<Table> tables = Table.fromWindows(wApi.getLoadedWindows());
            TableListBody tableListBody = new TableListBody(tables);
            ResponseMessage responseMessage = new ResponseMessage(MessageType.GET_TABLE_LIST, g.toJson(tableListBody));
            return g.toJson(responseMessage);
        } else if (message.getType() == MessageType.SHOW_WINDOW) {
            return showWindowCommand(message);
        } else if (message.getType() == MessageType.FOLD) {
            return foldCommand(message);
        } else if (message.getType() == MessageType.CHECK) {
            return checkCommand(message);
        } else if (message.getType() == MessageType.RAISE) {
            return raiseCommand(message);
        } else {
            System.out.println("Tipo não implementado");
        }
        return "";
    }

    private String showWindowCommand(RequestMessage message) {
        Table table = g.fromJson(message.getBody(), Table.class);
        boolean success = findWindowAndShow(table);
        ExecutedBody executedBody = new ExecutedBody(success);
        ResponseMessage responseMessage = new ResponseMessage(MessageType.SHOW_WINDOW, g.toJson(executedBody));
        return g.toJson(responseMessage);
    }

    private boolean findWindowAndShow(Table table) {
        Collection<Window> windows = wApi.getLoadedWindows();
        boolean success = false;
        for (Window window: windows) {
            if (window.getId().equals(table.getId())) {
                success = wApi.showWindow(window);
                break;
            }
        }
        return success;
    }

    private String foldCommand(RequestMessage message) {
        FoldCommand foldCommand = g.fromJson(message.getBody(), FoldCommand.class);
        boolean success = findWindowAndShow(foldCommand.getTable());
        if (success) {

        }
        ExecutedBody executedBody = new ExecutedBody(success);
        ResponseMessage responseMessage = new ResponseMessage(MessageType.FOLD, g.toJson(executedBody));
        return g.toJson(responseMessage);
    }

    private String checkCommand(RequestMessage message) {
        return null;
    }

    private String raiseCommand(RequestMessage message) {
        return null;
    }
}
