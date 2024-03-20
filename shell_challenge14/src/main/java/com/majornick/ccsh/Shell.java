package com.majornick.ccsh;

import com.majornick.ccsh.builtin.CommandExecutors;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Shell {
    private File currentDir;
    private String lastCmdOutput;


    public Shell() {
        currentDir = new File(".");
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(File currentDir) {
        this.currentDir = currentDir;
    }

    private static List<List<String>> splitPipedCommands(String commands) {
        List<List<String>> splittedCommands = new ArrayList<>();
        String[] pippedCommands = commands.split("\\|");
        for (String pippedCommand : pippedCommands) {
            pippedCommand = pippedCommand.trim();
            List<String> args = Arrays.asList(pippedCommand.split("\\s+"));
            splittedCommands.add(args);
        }
        return splittedCommands;
    }

    public void start() {
        while (true) {
            String args = getNewInputLine();
            if (args.length() == 0) {
                System.err.println("no input");
                System.exit(0);
            }
            var argsList = splitPipedCommands(args);
            for (int i = 0; i < argsList.size(); i++) {
                switchCmds(argsList.get(i), i != 0);
            }
        }
    }

    private String getNewInputLine() {
        System.out.print("ccsh> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void switchCmds(List<String> args, boolean isPiped) {
        switch (args.get(0)) {
            case "ls" -> CommandExecutors.execute_ls(this, args);
            case "pwd" -> CommandExecutors.execute_pwd(this);
            case "cd" -> CommandExecutors.execute_cd(this, args, isPiped);
            case "cat" -> CommandExecutors.execute_cat(this, args, isPiped);
            case "echo" -> CommandExecutors.execute_echo(this, args, isPiped);
            case "exit" -> System.exit(0);
            default -> System.out.println("No such file or directory");
        }
    }

    public void setLastCmdOutput(String lastCmdOutput) {
        this.lastCmdOutput = lastCmdOutput;
    }
}
