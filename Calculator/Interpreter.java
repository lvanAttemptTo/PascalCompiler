package Calculator;
import java.lang.reflect.InvocationTargetException;

public class Interpreter extends NodeVisitor{
    Parser parser;
    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public double interpret() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AST tree = this.parser.parse();
        return(this.visit(tree));
    }
}