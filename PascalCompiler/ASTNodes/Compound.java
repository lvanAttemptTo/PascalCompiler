package PascalCompiler.ASTNodes;
import java.util.ArrayList;

public class Compound extends AST{
    // represents a BEGIN END block
    public ArrayList<AST> children = new ArrayList<>();

    public String strRep() {
        return("Compound");
    }
}
