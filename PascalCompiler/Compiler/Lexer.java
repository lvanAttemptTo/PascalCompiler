package PascalCompiler.Compiler;

import PascalCompiler.Errors.LexerError;
import PascalCompiler.Globals.Keywords;
import PascalCompiler.Token.Token;
import PascalCompiler.Token.TokenType;

public class Lexer {
    String str;
    int pos = 0;
    Character currentChar;
    int currentLine = 1;
    int currentCol = 1;

    public Lexer(String str) {
        this.str = str;
        this.currentChar = str.charAt(0);
    }

    private void advance() {
        // System.out.println(this.currentChar);
        
        this.pos++;
        this.currentCol++;
        if (this.pos > this.str.length() - 1) {
            this.currentChar = null;
        } else {
            this.currentChar = this.str.charAt(this.pos);
        }
    }

    private void skipWhitespace() {
        while (this.currentChar != null && Character.isWhitespace(this.currentChar)) {
            if (Character.toString(this.currentChar).matches("[\n\r]")) {
                this.currentLine++;
                this.currentCol = -1;
            }
            this.advance();
        }
    }

    private void skipComment() {
        while(this.currentChar != '}') {
            this.advance();
        }
        this.advance();
    }

    private Token number() {
        StringBuilder result = new StringBuilder();
        while (this.currentChar != null && Character.isDigit(this.currentChar)) {
            result.append(this.currentChar);
            this.advance();
        }

        if (this.currentChar == '.') {
            result.append('.');
            this.advance();
            while (this.currentChar != null && Character.isDigit(this.currentChar)) {
                result.append(this.currentChar);
                this.advance();
            }
            return(new Token(TokenType.REAL_CONST, Double.parseDouble(result.toString()), this.currentLine, this.currentCol));
        } else {
            return(new Token(TokenType.INTEGER_CONST, Integer.parseInt(result.toString()), this.currentLine, this.currentCol));
        }

        
    }

    private Token id() {
        StringBuilder result = new StringBuilder();
        while (this.currentChar != null && Character.isLetterOrDigit(this.currentChar)) {
            result.append(this.currentChar);
            this.advance();
        }
        
        Token token = Keywords.map.get(result.toString().toUpperCase());
        
        if (token != null) {
            System.out.println(token.line);
            return(token);
        } else {
            token = new Token(TokenType.ID, result.toString(), this.currentLine, this.currentCol);
            System.out.println(token.line);
            return(token);
            
        }
    }

    public Token getNextToken() {
        while (this.currentChar != null) {
            if (Character.isWhitespace(this.currentChar)) {
                this.skipWhitespace();
                continue;
            }

            if (Character.isDigit(this.currentChar)) {
                return(this.number());
            }

            if (Character.isAlphabetic(this.currentChar)) {
                return(this.id());
            }

            if (this.currentChar == ':' && this.peek() == '=') {
                this.advance();
                this.advance();
                return(new Token(TokenType.ASSIGN, ":=", this.currentLine, this.currentCol));
            }

            if (this.currentChar == ';') {
                this.advance();
                return(new Token(TokenType.SEMI, ";", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '*') {
                this.advance();
                return(new Token(TokenType.MUL, "*", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '/') {
                this.advance();
                return(new Token(TokenType.REAL_DIV, "/", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '+') {
                this.advance();
                return(new Token(TokenType.PLUS, "+", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '-') {
                this.advance();
                return(new Token(TokenType.MINUS, "-", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '(') {
                this.advance();
                return(new Token(TokenType.LPAREN, "(", this.currentLine, this.currentCol));
            }

            if (this.currentChar == ')') {
                this.advance();
                return(new Token(TokenType.RPAREN, ")", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '.') {
                this.advance();
                return(new Token(TokenType.DOT, ".", this.currentLine, this.currentCol));
            }

            if (this.currentChar == '{') {
                this.advance();
                this.skipComment();
                continue;
            }

            if (this.currentChar == ':') {
                this.advance();
                return(new Token(TokenType.COLON, ":", this.currentLine, this.currentCol));
            }

            if (this.currentChar == ',') {
                this.advance();
                return(new Token(TokenType.COMMA, ",", this.currentLine, this.currentCol));
            }

            throw new LexerError(this.currentLine, this.currentCol);
        }

        return(new Token(TokenType.EOF, "EOF", this.currentLine, this.currentCol));
    }

    private Character peek() {
        int peekPos = this.pos + 1;
        if (peekPos > this.str.length() - 1) { 
            return(null);
        } else {
            return(this.str.charAt(peekPos));
        }
    }


    
}