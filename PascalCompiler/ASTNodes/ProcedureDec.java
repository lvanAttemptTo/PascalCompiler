package PascalCompiler.ASTNodes;

public class ProcedureDec extends Dec {
    public String procName;
    public Block blockNode;
    public AST[] params;

    public ProcedureDec(String procName, Block blockNode, AST[] params) {
        this.procName = procName;
        this.blockNode = blockNode;
        this.params = params;
    }
}
