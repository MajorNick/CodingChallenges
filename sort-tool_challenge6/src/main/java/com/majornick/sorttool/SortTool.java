package com.majornick.sorttool;

import com.majornick.sorttool.utils.SortType;

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
                lines = bufferedReader.lines().toList();
            }
            sortList(lines);
            if (reversed) {
                Collections.reverse(lines);
            }


        } catch (IOException e) {
            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + currentPath);
        }
    }

    private void sortList(List<String> lines) {
        switch (sortType) {
            case HEAP -> heapSort(lines);
            case QUICK -> quickSort(lines);
            case MERGE -> mergeSort(lines);
            case RADIX -> radixSort(lines);
            case RANDOM -> randomSort(lines);
        }
    }

    private void radixSort(List<String> lines) {
    }

    private void mergeSort(List<String> lines) {

    }

    private void quickSort(List<String> lines) {

    }

    private void randomSort(List<String> lines) {

    }

    private void heapSort(List<String> lines) {
    }


}
