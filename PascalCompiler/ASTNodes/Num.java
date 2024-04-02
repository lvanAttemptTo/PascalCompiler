package PascalCompiler.ASTNodes;

import PascalCompiler.Token.Token;

public class Num extends AST {
    public Token token;
    public String value;
    public Num(Token token) {
        this.token = token;
        this.value = token.value;
    }

    public double getValue() {
        return(Double.parseDouble(this.value));
    }

    public String strRep() {
        return("Num");
    }
}
