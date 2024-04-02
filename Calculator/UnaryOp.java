package Calculator;
public class UnaryOp extends AST {
    Token op;
    AST expr;

    public UnaryOp(Token op, AST expr) {
        this.op = op;
        this.expr = expr;
    }

    public String strRep() {
        return("UnaryOp");
    }
}