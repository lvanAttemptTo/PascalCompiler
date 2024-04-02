package PascalCompiler.ASTNodes;

public class VarDec extends Dec{
    public Var varNode;
    public Type typeNode;
    
    public VarDec (Var varNode, Type typeNode) {
        this.varNode = varNode;
        this.typeNode = typeNode;
    }
}
