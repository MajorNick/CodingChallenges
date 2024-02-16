package com.majornick.jsonparser.tokenizer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Tokenizer {

    private String content;
    private int curPosition;
    private int nest;

    public Tokenizer(String filename) {
        try {
            content = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int Tokenize() {
        return recursiveTokenize();
    }

    private int recursiveTokenize() {
        while (true) {

            if (curPosition >= content.length()) {
                nest--;
                return 1;
            }
            switch (content.charAt(curPosition)) {

                case '{' -> {
                    curPosition++;
                    nest++;
                    int res = Tokenize();

                    if (nest == 0 && curPosition >= content.length()) {

                        return res;
                    } else {
                        if (nest == 0) {
                            return 1;
                        }

                    }

                }
                case '}' -> {

                    curPosition++;
                    nest--;
                    return 0;
                }
                default -> {
                    curPosition++;
                    if (curPosition == content.length()) {
                        nest--;
                        return 1;
                    }
                }
            }
        }
    }

}
