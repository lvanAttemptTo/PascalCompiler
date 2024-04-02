package PascalCompiler.Compiler;
import java.lang.reflect.InvocationTargetException;

import PascalCompiler.ASTNodes.AST;

public class Interpreter extends NodeVisitor{
    Parser parser;
    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public Double interpret() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AST tree = this.parser.parse();
        if (tree == null) {
            return(null);
        }
        return(this.visit(tree));
    }
}