import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Handles the parsing of a single .vm file
 * Reads a VM command, parses the command into its lexical components,
 * and provides convenient access to these components
 */
public class Parser {

    private Scanner scanner;
    private String[] currentCommand;
    private final String C_PUSH = "C_PUSH";
    private final String C_POP = "C_POP";
    private final String C_ARITHMETIC = "C_ARITHMETIC";
    private final String C_GOTO = "C_GOTO";
    private final String C_LABEL = "C_LABEL";
    private final String C_IF = "C_IF";
    private final String C_RETURN = "C_RETURN";
    private final String C_FUNCTION = "C_FUNCTION";
    private final String C_CALL = "C_CALL";

    /**
     * C,tor - initializes the file to be read
     * @param filePath - of the file to be read(must be FULL path)
     */
    public Parser(String filePath) {
        File fileToParse = new File(filePath);
        try {
            this.scanner = new Scanner(fileToParse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if there are more commands in the file being read.
     * @return ture if has more commands, else false
     */
    public boolean hasMoreCommands() {
        if (scanner.hasNextLine()) {
            return true;
        }
        scanner.close();
        return false;
    }

    /**
     * advances the scanner in the file to the next command,
     * if its an empty line it continues to the next,and if there is a comment in the line it gets erased.
     */
    public void advance() {
        if (hasMoreCommands()) {
            String nextLine = scanner.nextLine();
            int indexToSlice = nextLine.indexOf("//");
            if (indexToSlice != -1) {
                if (nextLine.substring(0, indexToSlice).isEmpty() || nextLine.isEmpty()) {
                    advance();
                } else {
                    currentCommand = nextLine.substring(0, indexToSlice).split(" ");
                }
            } else if (nextLine.isEmpty()) {
                advance();
            } else {
                currentCommand = nextLine.split(" ");
            }
        }
    }

    /**
     * @return the command type of the current command.
     */
    public String commandType() {
        switch (currentCommand[0]) {
            case "push":
                return C_PUSH;
            case "pop":
                return C_POP;
            case "sub":
            case "add":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                return C_ARITHMETIC;
            default:
                throw new IllegalStateException("Unexpected value: " + currentCommand[0]);
        }
    }

    /**
     * @return the first argument of the current command
     */
    public String arg1() {
        if (commandType() != C_RETURN) {
            if (commandType() == C_ARITHMETIC) {
                return currentCommand[0];
            } else {
                return currentCommand[1];
            }
        }
        return null;
    }

    /**
     *
     * @return the second argument of the current command - only for pop,push,function,call
     */
    public String arg2() {
        String commandType = commandType();
        if (commandType.equals(C_POP) || commandType.equals(C_PUSH) || commandType.equals(C_CALL) || commandType.equals(C_FUNCTION))
            return currentCommand[2];
        return null;
    }

}