package PascalCompiler.ASTNodes;

import PascalCompiler.Token.Token;

public class Var extends AST {
    public Token token;
    public String value;
    public Var(Token token) {
        this.token = token;
        this.value = token.value;
    }

    public String strRep() {
        return("Var");
    }
}
