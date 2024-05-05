package com.majornick.redisserver_challenge8;

import com.majornick.redisserver_challenge8.message.Message;
import com.majornick.redisserver_challenge8.message.Type;
import org.junit.Assert;
import org.junit.Test;

public class RedisServerTest {

    @Test
    public void serverNullBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, null);
        Message actual = RedisServer.deserializeMessage("$-1\r\n");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void serverEmptyBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "");
        Message actual = RedisServer.deserializeMessage("$0\r\n\r\n");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "hello");
        Message actual = RedisServer.deserializeMessage("$5\r\nhello\r\n");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void serverBulkStringWithEndlnMessageDeserializationTest() {
        Message expected = new Message(Type.BULK_STRING, "Hello\r\nWorld");
        Message actual = RedisServer.deserializeMessage("$5\r\nHello\r\nWorld\r\n");
        Assert.assertEquals(expected, actual);
    }


}