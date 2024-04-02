package PascalCompiler.ASTNodes;

public class Block extends AST {
    public Dec[] declarations;
    public Compound compoundStatement;

    public Block(Dec[] declarations, Compound compoundStatement) {
        this.declarations = declarations;
        this.compoundStatement = compoundStatement;
    }
}
