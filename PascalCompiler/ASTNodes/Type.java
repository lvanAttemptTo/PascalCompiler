package PascalCompiler.ASTNodes;

import PascalCompiler.Token.Token;

public class Type extends AST {
    public Token token;
    public String type;

    public Type(Token token) {
        this.token = token;
        this.type = token.type.toString();
    }
}
