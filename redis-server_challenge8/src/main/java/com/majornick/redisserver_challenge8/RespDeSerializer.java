package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;

import java.util.ArrayList;
import java.util.List;

public class RespDeSerializer {

    private static final String TERMINATOR = "\r\n";

    public static Message deserializeMessage(String message) {
        if (message.isEmpty()) {
            return null;
        }
        Message result;
        switch (message.charAt(0)) {
            case '$' -> result = deserializeBulkMessages(message, Type.BULK_STRING);
            case '+' -> result = deserializeSimpleString(message);
            case '!' -> result = deserializeBulkMessages(message, Type.BULK_ERROR);
            case '-' -> result = deserializeSimpleError(message);
            case ':' -> result = deserializeNumber(message, Type.INTEGER);
            case '(' -> result = deserializeNumber(message, Type.BIG_NUMBER);
            case '*' -> result = deserializeArray(message);
            default -> result = null;
        }
        return result;
    }

    public static String serializeMessage(Message message) {
        String res = null;
        switch (message.getType()) {
            case BULK_STRING -> res = serializeBulkMessages(message, Type.BULK_STRING);
            case SIMPLE_STRING -> res = serilizeSimpleString(message);
            case BULK_ERROR -> res = serializeBulkMessages(message, Type.BULK_ERROR);
            case SIMPLE_ERROR -> res = serializeSimpleError(message);
            case INTEGER -> res = serializeNumber(message, Type.INTEGER);
            case BIG_NUMBER -> res = serializeNumber(message, Type.BIG_NUMBER);
            case ARRAY -> res = serializeArray(message);
        }
        return res;
    }


    private static String serializeBulkMessages(Message message, Type type) {
        StringBuilder res = new StringBuilder();
        res.append(type.getDiscriminator());
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


    private static Message deserializeBulkMessages(String message, Type type) {
        Message result = new Message();
        result.setType(type);
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
        return "+" + message.getContent() + TERMINATOR;
    }

    private static Message deserializeSimpleString(String message) {
        Message result = new Message();
        result.setType(Type.SIMPLE_STRING);
        result.setContent(message.substring(1, message.lastIndexOf(TERMINATOR)));
        return result;
    }

    private static String serializeSimpleError(Message message) {
        return "-" + message.getContent() + TERMINATOR;
    }

    private static Message deserializeSimpleError(String message) {
        Message result = new Message();
        result.setType(Type.SIMPLE_STRING);
        result.setContent(message.substring(1, message.lastIndexOf(TERMINATOR)));
        return result;
    }

    private static String serializeNumber(Message message, Type type) {
        return type.getDiscriminator() + message.getContent() + TERMINATOR;
    }

    private static Message deserializeNumber(String message, Type type) {
        Message result = new Message();
        result.setType(type);
        result.setContent(message.substring(1, message.lastIndexOf(TERMINATOR)));
        return result;
    }

    private static String serializeArray(Message message) {
        List<Message> messages = message.getMessages();
        StringBuilder res = new StringBuilder();
        res.append(Type.ARRAY.getDiscriminator()).append(messages.size()).append(TERMINATOR);
        messages.forEach(m -> res.append(serializeMessage(m)));
        return res.toString();
    }

    private static Message deserializeArray(String message) {

        List<Message> res = new ArrayList<>();
        int curPos = message.indexOf(TERMINATOR);
        int len = Integer.parseInt(message.substring(1, curPos));

        for (int i = 0; i < len; i++) {
            int nextPos = message.indexOf(TERMINATOR, curPos + 2);

            if (message.charAt(curPos + 2) == Type.BULK_STRING.getDiscriminator() || message.charAt(curPos + 2) == Type.BULK_ERROR.getDiscriminator()) {

                int curLen = Integer.parseInt(message.substring(curPos + 3, message.indexOf(TERMINATOR, curPos + 2)));
                nextPos = message.indexOf(TERMINATOR, curPos + 2 + curLen + 2);
            }
            String cur = message.substring(curPos + 2, nextPos + 2);
            res.add(deserializeMessage(cur));

            curPos = nextPos;
        }
        Message mes = new Message();
        mes.setType(Type.ARRAY);
        mes.setMessages(res);
        return mes;
    }

}
