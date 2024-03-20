package com.majornick.ccsh.builtin;

import com.majornick.ccsh.Shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class CommandExecutors {
    public static void execute_ls(Shell sh, List<String> args) {
        StringBuilder output = new StringBuilder();
        File[] files = sh.getCurrentDir().listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {

            output.append(String.format("%s\t", f.getName()));
        }
        output.append("\n");
        String s = output.toString();
        sh.setLastCmdOutput(s);
        System.out.println(s);

    }

    public static void execute_pwd(Shell sh) {

        try {
            String s = sh.getCurrentDir().getCanonicalPath();
            System.out.println(s);
            sh.setLastCmdOutput(s);
        } catch (IOException e) {
            System.err.println("can't get Canonical Path");
        }
    }

    public static void execute_cd(Shell sh, List<String> args, boolean isPiped) {
        if (args.size() != 2) {
            System.out.println("wrong arguments");
        }
        File curDir = sh.getCurrentDir();
        String dir = args.get(1);
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

    public static void execute_cat(Shell shell, List<String> args, boolean isPiped) {
        File curDir = shell.getCurrentDir();
        if (args.size() != 2) {
            System.out.println("wrong number of arguments");
        }
        String fileName = args.get(1);
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

    public static void execute_echo(Shell shell, List<String> args, boolean isPiped) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            String s = args.get(i);
            System.out.print(s);
            builder.append(s).append("\n");
        }
        shell.setLastCmdOutput(builder.toString());
    }
}
