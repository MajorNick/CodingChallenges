package com.majornick.sorttool.utils;

import picocli.CommandLine;

public class CLOptions {
    @CommandLine.Option(names = {"--uniq","-u"},defaultValue = "false")
    public  boolean uniq;
    @CommandLine.Option(names = {"--reversed","-r"},defaultValue = "false")
    public  boolean reversed;

    @CommandLine.Option(names = "-sort",defaultValue = "MERGE")
    public SortType sortType;

}
