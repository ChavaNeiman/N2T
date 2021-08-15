package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private String filePath;
    private Scanner scanner;
    private String currentCommand;
    private static final String A_COMMAND = "A_COMMAND";
    private static final String C_COMMAND = "C_COMMAND";
    private static final String L_COMMAND = "L_COMMAND";

    public Parser(String filePath) {
        this.filePath = filePath;
        File fileToParse = new File(filePath);
        try {
            this.scanner = new Scanner(fileToParse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean hasMoreCommands() {
        if (scanner.hasNextLine()) {
            return true;
        }
        scanner.close();
        return false;
    }

    public void advance() {
        if (hasMoreCommands()) {
            String nextLine = scanner.nextLine();
            nextLine = nextLine.replaceAll(" ", "");
            int indexToSlice = nextLine.indexOf("//");
            if (indexToSlice != -1) {
                if (nextLine.substring(0, indexToSlice).isEmpty() || nextLine.isEmpty()) {
                    advance();
                } else {
                    currentCommand = nextLine.substring(0, indexToSlice);
                }
            } else if (nextLine.isEmpty()) {
                advance();
            } else {
                currentCommand = nextLine;
            }
        }
    }

    public String commandType() {
        if (currentCommand.startsWith("@")) {
            return A_COMMAND;
        }
        if (currentCommand.startsWith("(") && currentCommand.endsWith(")")) {
            return L_COMMAND;
        }
        return C_COMMAND;
    }

    public String symbol() {
        if (commandType().equals(A_COMMAND)) {
            return currentCommand.replace("@", "");
        }
        if (commandType().equals(L_COMMAND)) {
            String cutCommand = currentCommand.replace("(", "");
            return cutCommand.replace(")", "");
        }
        return null;
    }

    public String dest() {
        if (commandType().equals(C_COMMAND)) {
            int indexToSlice = currentCommand.indexOf("=");
            if (indexToSlice != -1) {
                return currentCommand.substring(0, indexToSlice);
            }
        }
        return "null";
    }

    public String comp() {
        if (commandType().equals(C_COMMAND)) {
            int indexToStart = currentCommand.indexOf("=");
            int indexToEnd = currentCommand.indexOf(";");
            if (indexToEnd != -1) {
                return currentCommand.substring(indexToStart + 1, indexToEnd);
            } else {
                return currentCommand.substring(indexToStart + 1);
            }
        }
        return "null";
    }

    public String jump() {
        if (commandType().equals(C_COMMAND)) {
            int indexToSlice = currentCommand.indexOf(";");
            if (indexToSlice != -1) {
                return currentCommand.substring(indexToSlice + 1);
            }
        }
        return "null";
    }


    public String getCurrentCommand() {
        return currentCommand;
    }

    public void setCurrentCommand(String currentCommand) {
        this.currentCommand = currentCommand;
    }
}