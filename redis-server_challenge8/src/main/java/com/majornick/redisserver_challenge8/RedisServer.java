package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;

public class RedisServer {
    private static final String TERMINATOR = "\r\n";
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

    public static String serializeMessage(Message message) {
        String res = null;
        switch (message.getType()) {
            case BULK_STRING -> res = serializeBulkString(message);
            case SIMPLE_STRING -> res = serilizeSimpleString(message);
        }
        return res;
    }

    private static String serializeBulkString(Message message) {
        StringBuilder res = new StringBuilder();
        res.append("$");
        if (message.getContent() == null) {
            return res.append(-1)
                    .append(TERMINATOR)
                    .toString();
        }
        return res.append(message.getContent().length())
                .append(TERMINATOR)
                .append(message.getContent())
                .append(TERMINATOR)
                .toString();
    }

    private static Message deserializeBulkString(String message) {
        Message result = new Message();
        result.setType(Type.BULK_STRING);
        int grandLine = message.indexOf(TERMINATOR);
        int len = Integer.parseInt(message.substring(1, grandLine));
        if (len == -1) {
            result.setContent(null);
        } else {
            result.setContent(message.substring(grandLine + 2, message.lastIndexOf(TERMINATOR)));
        }
        return result;
    }

    private static String serilizeSimpleString(Message message) {
        StringBuilder res = new StringBuilder();
        return res.append("+").append(message.getContent()).append(TERMINATOR).toString();
    }
    private static Message deserializeSimpleString(String message) {
        Message result = new Message();
        result.setType(Type.SIMPLE_STRING);
        result.setContent(message.substring(1, message.lastIndexOf(TERMINATOR)));
        return result;
    }


}
