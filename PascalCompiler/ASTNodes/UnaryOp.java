package PascalCompiler.ASTNodes;

import PascalCompiler.Token.Token;

public class UnaryOp extends AST {
    public Token op;
    public AST expr;

    public UnaryOp(Token op, AST expr) {
        this.op = op;
        this.expr = expr;
    }

    public String strRep() {
        return("UnaryOp");
    }
}