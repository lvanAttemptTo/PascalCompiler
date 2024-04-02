package Calculator;
public class Token {
    TokenTypes type;
    Double value;

    public Token(TokenTypes type, Double value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenTypes type, int value) {
        this.type = type;
        this.value = (double)value;
    }

    public void print() {
        System.out.println("Type: " + this.type + ", Value: " + this.value);
    }
}