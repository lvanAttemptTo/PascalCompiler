package Calculator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public class Main {
    
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        
        // // Lexer lexer = new Lexer("2*(7+3)");
        // Interpreter interpreter = new Interpreter(lexer);
        // System.out.println(interpreter.expr());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            Lexer lexer = new Lexer(reader.readLine());
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        System.out.println(interpreter.interpret());
        }

    }
}