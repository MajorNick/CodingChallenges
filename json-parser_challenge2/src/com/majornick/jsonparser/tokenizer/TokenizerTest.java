package com.majornick.jsonparser.tokenizer;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TokenizerTest {

    @Test
    void testTokenizeStage1Invalid() {
        Tokenizer tokenizer1 = new Tokenizer("./tests/step1/invalid.json");

        Assert.assertEquals(tokenizer1.Tokenize(), 1);
    }

    @Test
    void testTokenizeStage1Valid() {
        Tokenizer tokenizer2 = new Tokenizer("./tests/step1/valid.json");
        Assert.assertEquals(tokenizer2.Tokenize(), 0);
    }

}