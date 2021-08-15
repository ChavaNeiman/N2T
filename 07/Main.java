/**
 * drives the process (VMTranslator)
 */
public class Main {
    public static void main(String[] args) {
        String filePath=args[0];
        Parser parser = new Parser(filePath);
        String outputFile = filePath.replace("vm","asm");
        CodeWriter codeWriter = new CodeWriter(outputFile);

        while(parser.hasMoreCommands()){
            parser.advance();
            if(parser.commandType().equals("C_ARITHMETIC")){
                codeWriter.WriteArithmetic(parser.arg1());
            }
            else{
                codeWriter.WritePushPop(parser.commandType(),parser.arg1(),parser.arg2());
            }
        }
        codeWriter.close();
    }
}
