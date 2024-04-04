package PascalCompiler.ASTNodes;
import PascalCompiler.Token.*;

public class ProcedureCall extends AST {
    public String procName;
    public AST[] params;
    public Token token;

    public ProcedureCall(String procName, AST[] params, Token token) {
        this.procName = procName;
        this.params = params;
        this.token = token;
    }
}