package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RedisServerTest {


    //Bulk String TESTS
    @Test
    public void serverNullBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, null);
        Message actual = RedisServer.deserializeMessage("$-1\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverEmptyBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "");
        Message actual = RedisServer.deserializeMessage("$0\r\n\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "hello");
        Message actual = RedisServer.deserializeMessage("$5\r\nhello\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringWithEndlineMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "Hello\r\nWorld");
        Message actual = RedisServer.deserializeMessage("$12\r\nHello\r\nWorld\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverNullBulkStringMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, null);
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverEmptyBulkStringMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, "");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, "hello");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringWithEndlineMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, "Hello\r\nWorld");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    // Simple STRING

    @Test
    public void serverEmptySimpleStringMessageDeserializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "");
        Message actual = RedisServer.deserializeMessage("+\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverSimpleStringMessageDeserializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "hello");
        Message actual = RedisServer.deserializeMessage("+hello\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverEmptySimpleStringMessageSerializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverSimpleStringMessageSerializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "hello");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    // Bulk Errors

    @Test
    public void serverBulkErrorMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_ERROR, " SYNTAX invalid syntax");
        Message actual = RedisServer.deserializeMessage("!21\r\n SYNTAX invalid syntax\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkErrorMessageSerializationTest() {
        Message expected = new Message(Type.BULK_ERROR, " SYNTAX invalid syntax");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    // Simple Error

    @Test
    public void serverSimpleErrorMessageSerializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "Error message");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverSimpleErrorMessageDeserializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "Error message");
        Message actual = RedisServer.deserializeMessage("-Error message\r\n");
        assertEquals(expected, actual);
    }

    //Integers

    @Test
    public void serverIntegerMessageSerializationTest() {
        Message expected = new Message(Type.INTEGER, "436");
        String serialized = RedisServer.serializeMessage(expected);
        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverIntegerMessageDeserializationTest() {
        int expected = -35;
        Message actual = RedisServer.deserializeMessage(":-35\r\n");
        assertEquals(expected, Integer.parseInt(actual.getContent()));
    }

    //Array
    @Test
    public void serverArrayMessageSerializationTest() {
        List<Message> messages = new ArrayList<>();
        Message expected = new Message();
        expected.setType(Type.ARRAY);
        expected.setMessages(messages);
        messages.add(new Message(Type.INTEGER, "436"));
        messages.add(new Message(Type.SIMPLE_STRING, "Error message"));
        messages.add(new Message(Type.BULK_STRING, "Hello\r\nWorld"));
        String serialized = RedisServer.serializeMessage(expected);

        Message actual = RedisServer.deserializeMessage(serialized);
        assertEquals(expected, actual);
        assertEquals(expected.getMessages(), actual.getMessages());
    }

    @Test
    public void serverArrayMessageDeserializationTest() {
        List<Message> messages = new ArrayList<>();
        Message expected = new Message();
        expected.setType(Type.ARRAY);
        expected.setMessages(messages);
        messages.add(new Message(Type.INTEGER, "436"));
        messages.add(new Message(Type.SIMPLE_STRING, "Error message"));
        messages.add(new Message(Type.BULK_STRING, "Hello\r\nWorld"));
        Message actual = RedisServer.deserializeMessage("*3\r\n:436\r\n-Error message\r\n$12\r\nHello\r\nWorld\r\n");
        System.out.println(actual.getMessages());
        assertEquals(expected.getMessages(), actual.getMessages());
    }

}