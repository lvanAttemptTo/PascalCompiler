package PascalCompiler.Errors;

import PascalCompiler.Token.TokenType;

public class ParserError extends Error {
    public ParserError(String message, int line, int col) {
        super(String.format("%s %s:%s", message, line, col));
    }
}