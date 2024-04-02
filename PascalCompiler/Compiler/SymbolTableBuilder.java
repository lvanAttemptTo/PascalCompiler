package PascalCompiler.Compiler;

import PascalCompiler.ASTNodes.Assign;
import PascalCompiler.ASTNodes.BinOp;
import PascalCompiler.ASTNodes.Block;
import PascalCompiler.ASTNodes.Compound;
import PascalCompiler.ASTNodes.NoOp;
import PascalCompiler.ASTNodes.Num;
import PascalCompiler.ASTNodes.ProcedureDec;
import PascalCompiler.ASTNodes.Program;
import PascalCompiler.ASTNodes.UnaryOp;
import PascalCompiler.ASTNodes.Var;
import PascalCompiler.ASTNodes.VarDec;
import PascalCompiler.Symbols.Symbol;
import PascalCompiler.Symbols.SymbolTable;
import PascalCompiler.Symbols.VarSymbol;

public class SymbolTableBuilder extends NodeVisitor {
    public SymbolTable symTab = new SymbolTable();
    public SymbolTableBuilder() {

    }

    public Double visitBlock(Block node) {
        for (int i = 0; i < node.declarations.length; i++) {
            this.visit(node.declarations[i]);
        }
        this.visit(node.compoundStatement);
        return(null);
    }

    public Double visitProgram(Program node) {
        this.visit(node.block);
        return(null);
    }

    public Double visitBinOp(BinOp node) {
        this.visit(node.left);
        this.visit(node.right);
        return(null);
    }

    public Double visitNum(Num node) {
        return(null);
    }

    public Double visitUnaryOp(UnaryOp node) {
        this.visit(node.expr);
        return(null);
    }

    public Double visitCompound(Compound node) {
        for (int i = 0; i < node.children.size(); i++) {
            this.visit(node.children.get(i));
        }
        return(null);
    }

    public Double visitNoOp(NoOp node) {
        return(null);
    }

    public Double visitDec(VarDec node) {
        String typeName = node.typeNode.type;
        Symbol typeSymbol = this.symTab.lookup(typeName);
        String varName = node.varNode.value;
        Symbol varSymbol = new VarSymbol(varName, typeSymbol);
        this.symTab.insert(varSymbol);
        return(null);
    }

    public Double visitAssign(Assign node) {
        String varName = node.left.value;
        Symbol varSymbol = this.symTab.lookup(varName);
        if (varSymbol == null) {
            throw new Error("Variable: " + varName + " is not declared");
        }
        this.visit(node.right);
        return(null);
    }

    public Double visitVar(Var node) {
        String varName = node.value;
        Symbol varSymbol = this.symTab.lookup(varName);
        if (varSymbol == null) {
            throw new Error("Variable: " + varName + " is not declared at line " + node.token.line);
        }

        return(null);
    }

    public Double visitProcedureDec(ProcedureDec node) {
        return(null);
    }
}
