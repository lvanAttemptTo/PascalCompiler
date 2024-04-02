package PascalCompiler.ASTNodes;

public class Program extends AST {
    public String name;
    public Block block;
    public Program(String name, Block block) {
        this.name = name;
        this.block = block;
    }
}
