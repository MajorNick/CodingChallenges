package com.majornick.sorttool.utils;

import picocli.CommandLine;

public enum SortType {
    RANDOM,
    RADIX,
    MERGE,
    QUICK,
    HEAP;
    public static class SortTypeConverter implements CommandLine.ITypeConverter<SortType> {
        @Override
        public SortType convert(String value) throws Exception {
            for (SortType sortType : SortType.values()) {
                if (sortType.name().equalsIgnoreCase(value)) {
                    return sortType;
                }
            }
            throw new IllegalArgumentException("Invalid sort type: " + value);
        }
    }
}
