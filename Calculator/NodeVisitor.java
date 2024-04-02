package Calculator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NodeVisitor {
    public double visit(AST node) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (node.strRep().equals("Num")) {
            return(((Num)node).value);
        } else if (node.strRep().equals("BinOp")) {
            BinOp opNode = (BinOp)node;
            if (opNode.op.type == TokenTypes.PLUS) {
                return(this.visit(opNode.left) + this.visit(opNode.right));
            } else if (opNode.op.type == TokenTypes.MINUS) {
                return(this.visit(opNode.left) - this.visit(opNode.right));
            } else if (opNode.op.type == TokenTypes.DIV) {
                return(this.visit(opNode.left) / this.visit(opNode.right));
            } else if (opNode.op.type == TokenTypes.MUL) {
                return(this.visit(opNode.left) * this.visit(opNode.right));
            }
            throw new Error();
        } else if (node.strRep().equals("UnaryOp")) {
            UnaryOp uNode = (UnaryOp)node;
            TokenTypes op = uNode.op.type;
            if (op == TokenTypes.PLUS) {
                return(+this.visit(uNode.expr));
            } else if (op == TokenTypes.MINUS) {
                return(-this.visit(uNode.expr));
            }
        }
        throw new Error();
    }
}
