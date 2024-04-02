package Calculator;
public class Num extends AST {
    Token token;
    double value;
    public Num(Token token) {
        this.token = token;
        this.value = token.value;
    }

    public String strRep() {
        return("Num");
    }
}
