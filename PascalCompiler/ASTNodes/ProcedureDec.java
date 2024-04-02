package PascalCompiler.ASTNodes;

public class ProcedureDec extends Dec {
    public String procName;
    public Block blockNode;

    public ProcedureDec(String procName, Block blockNode) {
        this.procName = procName;
        this.blockNode = blockNode;
    }
}
