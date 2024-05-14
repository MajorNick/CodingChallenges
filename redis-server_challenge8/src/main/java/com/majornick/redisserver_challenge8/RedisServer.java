package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class RedisServer {


    public RedisServer(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            while (true) {
                new ClientHandler(socket.accept()).run();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
