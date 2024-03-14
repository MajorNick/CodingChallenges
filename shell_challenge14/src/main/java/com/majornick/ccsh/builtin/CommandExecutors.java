package com.majornick.ccsh.builtin;

import com.majornick.ccsh.Shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class CommandExecutors {
    public static void execute_ls(Shell sh, String[] args) {
        File[] files = sh.getCurrentDir().listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            System.out.printf("%s\t", f.getName());
        }
        System.out.println();
    }

    public static void execute_pwd(Shell sh) {

        try {
            System.out.println(sh.getCurrentDir().getCanonicalPath());
        } catch (IOException e) {
            System.err.println("can't get Canonical Path");
        }
    }

    public static void execute_cd(Shell sh, String[] args) {
        if (args.length != 2) {
            System.out.println("wrong arguments");
        }
        File curDir = sh.getCurrentDir();
        String dir = args[1];
        File newDir = getExactName(curDir, dir);
        if (newDir.isDirectory()) {
            sh.setCurrentDir(newDir);
            return;
        }
        if (newDir.isFile()) {
            System.out.println("Not a directory");
            return;
        }
        System.out.println("No such file or directory");
    }

    public static void execute_cat(Shell shell, String[] args) {
        File curDir = shell.getCurrentDir();
        if (args.length != 2) {
            System.out.println("wrong number of arguments");
        }
        String fileName = args[1];
        File file = getExactName(curDir, fileName);
        if (!file.isFile()) {
            System.out.println("No such file or directory");
            return;
        }
        try {
            String content = Files.readString(file.getAbsoluteFile().toPath());
            System.out.println(content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static File getExactName(File curDir, String fileName) {
        File file;
        if (fileName.startsWith("/")) {
            return new File(fileName);
        } else {
            return new File(String.format("%s/%s", curDir.toString(), fileName));
        }

    }
}
