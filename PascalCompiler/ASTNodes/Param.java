package PascalCompiler.ASTNodes;

public class Param extends AST {
    Var varNode;
    Type typeNode;

    public Param(Var varNode, Type typeNode) {
        this.varNode = varNode;
        this.typeNode = typeNode;
    }
}
