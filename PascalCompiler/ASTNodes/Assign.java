package PascalCompiler.ASTNodes;

import PascalCompiler.Token.Token;

public class Assign extends AST{
    public Var left;
    public AST right;
    public Token op;
    public Assign(Var left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public String strRep() {
        return("Assign");
    }

}
