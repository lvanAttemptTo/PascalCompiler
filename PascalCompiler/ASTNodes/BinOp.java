package PascalCompiler.ASTNodes;

import PascalCompiler.Token.Token;

public class BinOp extends AST {
    public AST left;
    public AST right;
    public Token op;
    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public String strRep() {
        return("BinOp");
    }
}
