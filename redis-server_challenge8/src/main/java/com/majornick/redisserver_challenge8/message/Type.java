package com.majornick.redisserver_challenge8.message;

public enum Type {
    SIMPLE_STRING('+'),
    SIMPLE_ERROR('-'),
    INTEGER(':'),
    BULK_STRING('$'),
    ARRAY('*'),
    NULL('_'),
    BOOLEAN('#'),
    DOUBLE(','),
    BIG_NUMBER('('),
    BULK_ERROR('!'),
    VERBATIM_STRING('='),
    MAP('%'),
    SET('~'),
    PUSHES('>');

    private final char discriminator;

    Type(char discriminator) {
        this.discriminator = discriminator;
    }

    public char getDiscriminator() {
        return discriminator;
    }
}
