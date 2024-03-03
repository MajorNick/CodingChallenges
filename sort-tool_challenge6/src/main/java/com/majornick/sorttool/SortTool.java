package com.majornick.sorttool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class SortTool {
    private final String filename;

    public SortTool(String filename) {
        this.filename = filename;
    }

    public void process() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));) {
            ArrayList<String> lines = (ArrayList<String>) bufferedReader.lines().collect(Collectors.toList());
            Collections.sort(lines);
            for (int i = 0; i < 5; i++) {
                System.out.println(lines.get(i));
            }
        } catch (IOException e) {
            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + currentPath);
        }
    }

}
