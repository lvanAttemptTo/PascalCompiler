package PascalCompiler.Token;

public enum TokenType {
    BEGIN,
    INTEGER,
    DIV,
    PROGRAM,
    VAR,
    REAL,
    PROCEDURE,
    END,

    DOT,
    ASSIGN,
    SEMI,
    ID,
    
    PLUS,
    MINUS,
    MUL,
    REAL_DIV,
    LPAREN,
    RPAREN,
    
    
    INTEGER_CONST,
    REAL_CONST,
    COMMA,
    COLON,
    
    EOF;
}