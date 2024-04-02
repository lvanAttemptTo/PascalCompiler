package Calculator;
public class Lexer {
    String str;
    int pos = 0;
    Character currentChar;

    public Lexer(String str) {
        this.str = str;
        this.currentChar = str.charAt(0);
    }

    private void advance() {
        this.pos++;

        if (this.pos > this.str.length() - 1) {
            this.currentChar = null;
        } else {
            this.currentChar = this.str.charAt(this.pos);
        }
    }

    private void skipWhitespace() {
        while (this.currentChar != null && Character.isWhitespace(this.currentChar)) {
            this.advance();
        }
    }

    private int integer() {
        StringBuilder result = new StringBuilder();
        while (this.currentChar != null && Character.isDigit(this.currentChar)) {
            result.append(this.currentChar);
            this.advance();

            if (this.currentChar != null && Character.isWhitespace(this.currentChar)) {
                this.skipWhitespace();
            }
        }

        return(Integer.parseInt(result.toString()));
    }

    public Token getNextToken() {
        while (this.currentChar != null) {
            if (Character.isWhitespace(this.currentChar)) {
                this.skipWhitespace();
                continue;
            }

            if (Character.isDigit(this.currentChar)) {
                return(new Token(TokenTypes.INTEGER, this.integer()));
            }

            if (this.currentChar == '*') {
                this.advance();
                return(new Token(TokenTypes.MUL, null));
            }

            if (this.currentChar == '/') {
                this.advance();
                return(new Token(TokenTypes.DIV, null));
            }

            if (this.currentChar == '+') {
                this.advance();
                return(new Token(TokenTypes.PLUS, null));
            }

            if (this.currentChar == '-') {
                this.advance();
                return(new Token(TokenTypes.MINUS, null));
            }

            if (this.currentChar == '(') {
                this.advance();
                return(new Token(TokenTypes.LPAREN, null));
            }

            if (this.currentChar == ')') {
                this.advance();
                return(new Token(TokenTypes.RPAREN, null));
            }

            throw new Error("Unkown input");
        }

        return(new Token(TokenTypes.EOF, null));
    }
}