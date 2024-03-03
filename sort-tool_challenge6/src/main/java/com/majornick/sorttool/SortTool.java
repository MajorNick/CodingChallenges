package com.majornick.sorttool;

import com.majornick.sorttool.utils.SortType;
import com.majornick.sorttool.utils.Sorts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SortTool {
    private final String filename;
    private final SortType sortType;
    private final boolean reversed;
    private final boolean unique;


    public SortTool(String filename, SortType sortType, boolean reversed, boolean unique) {
        this.filename = filename;
        this.sortType = sortType;
        this.reversed = reversed;
        this.unique = unique;
    }

    public void process() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            List<String> lines;
            if (unique) {
                lines = bufferedReader.lines().distinct().collect(Collectors.toList());
            } else {
                lines = bufferedReader.lines().collect(Collectors.toList());
            }
            sortList(lines);
            if (reversed) {
                Collections.reverse(lines);
            }
            lines.forEach(System.out::println);
        } catch (IOException e) {
            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + currentPath);
        }
    }

    private void sortList(List<String> lines) {
        switch (sortType) {
            case HEAP -> Sorts.heapSort(lines);
            case QUICK -> Sorts.quickSort(lines);
            case MERGE -> Sorts.mergeSort(lines);
            case RADIX -> Sorts.radixSort(lines);
            case RANDOM -> Sorts.randomSort(lines);
        }
    }



}
