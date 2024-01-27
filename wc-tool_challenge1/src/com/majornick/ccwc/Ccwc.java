package com.majornick.ccwc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ccwc {

    private final List<Character> ALL_ARGS = new ArrayList<>(Arrays.asList('c', 'l', 'w', 'm'));
    private final String[] args;
    private final List<File> files;
    private List<Character> arguments;


    public Ccwc(String[] args) {
        this.args = args;
        files = findFilesIfExists();
        arguments = findArgumentsIfExists();
        if (arguments.isEmpty()) {
            arguments = ALL_ARGS;
        }

    }

    public void process() {
        if (files.isEmpty()) {
            StringBuilder text = new StringBuilder();
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = System.in.read(buffer)) != -1) {
                    String chunk = new String(buffer, 0, bytesRead);
                    text.append(chunk);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            processByArguments(text.toString());
        } else {
            for (File f : files) {
                try {
                    processByArguments(Files.readString(f.toPath()));
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

    private void processByArguments(String text) {

        StringBuilder result = new StringBuilder();
        for (Character c : arguments) {
            switch (c) {
                case 'c' -> result.append(getSizeInBytes(text));
                case 'l' -> result.append(getNewLines(text));
                case 'w' -> result.append(getNumberOfWords(text));
                case 'm' -> result.append(String.format("%d character", text.length()));
                default -> System.err.println("wrong argument:" + c);
            }
        }
        System.out.println(result);
    }

    private List<File> findFilesIfExists() {
        List<File> files = new ArrayList<>();
        for (String arg : args) {
            if (!arg.startsWith("-")) {
                try {
                    File file = new File(arg);
                    files.add(file);
                } catch (NullPointerException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return files;
    }

    private List<Character> findArgumentsIfExists() {
        List<Character> arguments = new ArrayList<>();
        if (args.length > 0 && args[0].startsWith("-")) {
            for (Character c : args[0].substring(1).toCharArray()) {
                arguments.add(c);
            }
        }
        return arguments;
    }

    private String getSizeInBytes(String text) {
        return String.format("%d bytes ", text.getBytes().length);
    }

    private String getNewLines(String text) {
        return String.format("%d new lines ", text.lines().count());
    }

    private String getNumberOfWords(String text) {
        return String.format("%d words ", text.split("\\s+").length);
    }
}


