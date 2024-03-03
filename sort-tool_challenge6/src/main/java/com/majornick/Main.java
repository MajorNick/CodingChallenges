package com.majornick;

import com.majornick.sorttool.SortTool;
import com.majornick.sorttool.utils.CLOptions;
import com.majornick.sorttool.utils.SortType;
import picocli.CommandLine;


public class Main {

    public static void main(String[] args) throws Exception {
        CLOptions options = new CLOptions();
        CommandLine cmd = new CommandLine(options);
        cmd.registerConverter(SortType.class,new SortType.SortTypeConverter());
        cmd.parseArgs(args);

        SortTool sortTool = new SortTool("./words.txt",options.sortType,options.reversed, options.uniq);
        sortTool.process();
    }
}