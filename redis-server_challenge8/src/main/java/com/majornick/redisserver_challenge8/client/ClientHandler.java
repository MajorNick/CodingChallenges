package com.majornick.redisserver_challenge8.client;

import com.majornick.redisserver_challenge8.RespDeSerializer;
import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private static HashMap<String, String> DB = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String line;
            while (true) {
                int k = reader.read();
                System.out.println(k);
                if (k == -1) break;
                sb.append((char) k);
            }
            execCommand(sb.toString(), new PrintWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void execCommand(String args, PrintWriter printer) {
        String[] splitted = args.split(" ");
        String cmd = splitted[0];
        cmd = cmd.toUpperCase();
        System.out.println(args);
        switch (cmd) {
            case "PING" -> printer.write("PONG");
            case "ECHO" -> printer.write(splitted[1]);
            case "SET" -> printer.write(RespDeSerializer.serializeMessage(executeSetCmd(splitted)));
            case "GET" -> printer.write(RespDeSerializer.serializeMessage(executeGetCmd(splitted)));
        }
    }

    private Message executeGetCmd(String[] args) {
        if (args.length != 3) {
            return Message
                    .builder()
                    .type(Type.SIMPLE_ERROR)
                    .content(String.format("wrong number of arguments. expected 3 got %d", args.length))
                    .build();
        }
        return Message.builder()
                .type(Type.BULK_STRING)
                .content(DB.get(args[1]))
                .build();
    }

    private Message executeSetCmd(String[] args) {
        if (args.length != 3) {
            return Message
                    .builder()
                    .type(Type.SIMPLE_ERROR)
                    .content(String.format("wrong number of arguments. expected 3 got %d", args.length))
                    .build();
        }
        DB.put(args[1], args[2]);
        return Message
                .builder()
                .type(Type.SIMPLE_STRING)
                .content("OK")
                .build();
    }

}
