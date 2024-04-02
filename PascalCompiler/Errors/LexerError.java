package PascalCompiler.Errors;

public class LexerError extends Error {

    public LexerError(int line, int col) {
        super(String.format("Unexpected token at line %s:%s", line, col));
    }

}