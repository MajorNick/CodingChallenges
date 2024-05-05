package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;

public class RedisServer {

    public static Message deserializeMessage(String message) {
        if (message.isEmpty()) {
            return null;
        }
        Message result;
        switch (message.charAt(0)) {
            case '$' -> result = deserializeBulkString(message);
            case '+' -> result = deserializeSimpleString(message);
            default -> result = null;
        }
        return result;
    }


    private static Message deserializeBulkString(String message) {
        Message result = new Message();
        result.setType(Type.BULK_STRING);
        int grandLine = message.indexOf("\r\n");
        int len = Integer.parseInt(message.substring(1, grandLine));
        if (len == -1) {
            result.setContent(null);
        } else {
            result.setContent(message.substring(grandLine + 2, message.lastIndexOf("\r\n")));
        }
        return result;
    }

    private static Message deserializeSimpleString(String message) {
        return new Message();
    }


}
