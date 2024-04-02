package PascalCompiler.Errors;

public class SamanticError extends Error {
    public SamanticError(String message, int line, int col) {
        super(String.format("%s %s:%s",message, line, col));
    }

    
}
