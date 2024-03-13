package com.majornick.ccsh;

import com.majornick.ccsh.builtin.CommandExecutors;

import java.util.Scanner;

public class Shell {
    public void start() {
        String[] args = getNewInputLine();
        if (args.length == 0) {
            System.err.println("no input");
            System.exit(0);
        }
        switchCmds(args);
    }

    private String[] getNewInputLine() {
        System.out.print("ccsh> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().split(" ");
    }

    private void switchCmds(String[] args) {
        switch (args[0]) {
            case "ls" -> CommandExecutors.execute_ls(args);
        }
    }
}
