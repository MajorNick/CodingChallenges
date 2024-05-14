package com.majornick.redisserver_challenge8.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printer = new PrintWriter(socket.getOutputStream());) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] args = line.split(" ");

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    private void execCommand(String[] args, PrintWriter printer) {
        String cmd = args[0];
        switch (cmd) {
            case "PING":
                printer.write("PONG");
                break;
        }
    }
}
