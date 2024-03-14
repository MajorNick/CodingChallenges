package com.majornick.ccsh;

import com.majornick.ccsh.builtin.CommandExecutors;

import java.io.File;
import java.util.Scanner;

public class Shell {
    private File currentDir;

    public Shell() {
        currentDir = new File(".");
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(File currentDir) {
        this.currentDir = currentDir;
    }

    public void start() {
        while (true) {
            String[] args = getNewInputLine();
            if (args.length == 0) {
                System.err.println("no input");
                System.exit(0);
            }
            switchCmds(args);
        }
    }

    private String[] getNewInputLine() {
        System.out.print("ccsh> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().split("\\s+");
    }

    private void switchCmds(String[] args) {
        switch (args[0]) {
            case "ls" -> CommandExecutors.execute_ls(this, args);
            case "pwd" -> CommandExecutors.execute_pwd(this);
            case "cd" -> CommandExecutors.execute_cd(this, args);
            case "cat" -> CommandExecutors.execute_cat(this, args);
            case "exit" -> System.exit(0);
            default -> System.out.println("No such file or directory");
        }
    }
}
