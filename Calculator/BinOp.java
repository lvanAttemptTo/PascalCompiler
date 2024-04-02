package Calculator;
public class BinOp extends AST {
    AST left;
    AST right;
    Token op;
    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public String strRep() {
        return("BinOp");
    }
}
