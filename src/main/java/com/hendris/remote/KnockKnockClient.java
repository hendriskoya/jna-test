package com.hendris.remote;

import com.hendris.Table;
import java.io.*;
import java.net.*;
import java.util.List;

import com.google.gson.Gson;

public class KnockKnockClient {
    public static void main(String[] args) throws IOException {
        
        /*if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }*/

        //String hostName = args[0];
        String hostName = "localhost";
        //int portNumber = Integer.parseInt(args[1]);
        int portNumber = Integer.parseInt("9898");

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            Gson g = new Gson();

            Table tableTest = null;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;
                if (fromServer.startsWith("{")) {
                    ResponseMessage responseMessage = g.fromJson(fromServer, ResponseMessage.class);
                    if (responseMessage.getType() == MessageType.GET_TABLE_LIST) {
                        TableListBody tableListBody = g.fromJson(responseMessage.getBody(), TableListBody.class);
                        for (Table table: tableListBody.getTables()) {
                            System.out.println(table.toString());
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tableTest = tableListBody.getTables().get(0);
                        /*Message requestMessage = new RequestMessage(MessageType.SHOW_WINDOW, g.toJson(table));
                        String jsonMessage = g.toJson(requestMessage);
                        out.println(jsonMessage);*/
                    } else if (responseMessage.getType() == MessageType.SHOW_WINDOW) {
                        System.out.println(responseMessage.getBody());
                    }
                }
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    if (fromUser.equals(("1000"))) {
                        Message requestMessage = new RequestMessage(MessageType.GET_TABLE_LIST, "");
                        String jsonMessage = g.toJson(requestMessage);
                        out.println(jsonMessage);
                    } else if (fromUser.equals("2000")) {
                        Message requestMessage = new RequestMessage(MessageType.SHOW_WINDOW, g.toJson(tableTest));
                        String jsonMessage = g.toJson(requestMessage);
                        out.println(jsonMessage);
                    } else {
                        out.println(fromUser);
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}