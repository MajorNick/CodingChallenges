package com.majornick.ccsh.builtin;

import java.io.File;

public class CommandExecutors {
    public static void execute_ls(String[] args) {
        File curDirectory = new File(".");
        File[] files = curDirectory.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            System.out.print(f.getName() + "\t");
        }
    }
}
