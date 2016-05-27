package com.hendris.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {
        new KnockKnockServer();
    }

    private Engine protocol = new Engine();

    public KnockKnockServer() {
        /*if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }*/

        //int portNumber = Integer.parseInt(args[0]);
        int portNumber = Integer.parseInt("9898");

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String inputLine, outputLine;

            /*String jsonTables = new Engine().getJsonTables();
            Message message = new RequestMessage(MessageType.GET_TABLE_LIST, jsonTables);
            Gson g = new Gson();
            out.println(g.toJson(message));*/
            out.println("Welcome to Knock!");

            listen(out, in);

            // Initiate conversation with client
            /*KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }*/
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private void listen(PrintWriter out, BufferedReader in) throws IOException {
        String inputLine;
        String outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Request incoming...");
            outputLine = protocol.processMessage(inputLine);
            out.println(outputLine);
        }
    }
}