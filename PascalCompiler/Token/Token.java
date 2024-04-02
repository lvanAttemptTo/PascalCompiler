package PascalCompiler.Token;

public class Token {
    public TokenType type;
    public String value;
    public int line = 0;
    public int col = 0;

    public Token(TokenType type, double value, int line, int col) {
        this.type = type;
        this.value = Double.toString(value);
        this.line = line;
        this.col = col;
    }

    public Token(TokenType type, int value, int line, int col) {
        this.type = type;
        this.value = Integer.toString(value);
        this.line = line;
        this.col = col;
    }

    public Token(TokenType type, String value, int line, int col) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.col = col;
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public double getValue() {
        return(Double.parseDouble(value));
    }

    public void print() {
        System.out.println("Type: " + this.type + ", Value: " + this.value);
    }
}