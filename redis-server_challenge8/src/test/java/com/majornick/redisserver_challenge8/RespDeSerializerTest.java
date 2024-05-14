package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RespDeSerializerTest {


    //Bulk String TESTS
    @Test
    public void serverNullBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, null);
        Message actual = RespDeSerializer.deserializeMessage("$-1\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverEmptyBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "");
        Message actual = RespDeSerializer.deserializeMessage("$0\r\n\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "hello");
        Message actual = RespDeSerializer.deserializeMessage("$5\r\nhello\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringWithEndlineMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "Hello\r\nWorld");
        Message actual = RespDeSerializer.deserializeMessage("$12\r\nHello\r\nWorld\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverNullBulkStringMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, null);
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverEmptyBulkStringMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, "");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, "hello");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringWithEndlineMessageSerializationTest() {
        Message expected = new Message(Type.BULK_STRING, "Hello\r\nWorld");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    // Simple STRING

    @Test
    public void serverEmptySimpleStringMessageDeserializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "");
        Message actual = RespDeSerializer.deserializeMessage("+\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverSimpleStringMessageDeserializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "hello");
        Message actual = RespDeSerializer.deserializeMessage("+hello\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverEmptySimpleStringMessageSerializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverSimpleStringMessageSerializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "hello");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    // Bulk Errors

    @Test
    public void serverBulkErrorMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_ERROR, " SYNTAX invalid syntax");
        Message actual = RespDeSerializer.deserializeMessage("!21\r\n SYNTAX invalid syntax\r\n");
        assertEquals(expected, actual);
    }

    @Test
    public void serverBulkErrorMessageSerializationTest() {
        Message expected = new Message(Type.BULK_ERROR, " SYNTAX invalid syntax");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    // Simple Error

    @Test
    public void serverSimpleErrorMessageSerializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "Error message");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverSimpleErrorMessageDeserializationTest() {
        Message expected = new Message(Type.SIMPLE_STRING, "Error message");
        Message actual = RespDeSerializer.deserializeMessage("-Error message\r\n");
        assertEquals(expected, actual);
    }

    //Integers

    @Test
    public void serverIntegerMessageSerializationTest() {
        Message expected = new Message(Type.INTEGER, "436");
        String serialized = RespDeSerializer.serializeMessage(expected);
        Message actual = RespDeSerializer.deserializeMessage(serialized);
        assertEquals(expected, actual);
    }

    @Test
    public void serverIntegerMessageDeserializationTest() {
        int expected = -35;
        Message actual = RespDeSerializer.deserializeMessage(":-35\r\n");
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
        String serialized = RespDeSerializer.serializeMessage(expected);

        Message actual = RespDeSerializer.deserializeMessage(serialized);
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
        Message actual = RespDeSerializer.deserializeMessage("*3\r\n:436\r\n-Error message\r\n$12\r\nHello\r\nWorld\r\n");

        assertEquals(expected.getMessages(), actual.getMessages());
    }

}