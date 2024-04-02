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
import PascalCompiler.Errors.SamanticError;
import PascalCompiler.Symbols.Symbol;
import PascalCompiler.Symbols.SymbolTable;
import PascalCompiler.Symbols.VarSymbol;

public class SamanticAnalyzer extends NodeVisitor {
    public SymbolTable symTab = new SymbolTable();

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
        if (this.symTab.lookup(varName) != null) {
            throw new SamanticError("Variable " + varName + " is defined twice", node.varNode.token.line, node.varNode.token.col);
        }
        this.symTab.insert(varSymbol);
        return(null);
    }


    public Double visitVar(Var node) {
        String varName = node.value;
        Symbol varSymbol = this.symTab.lookup(varName);
        if (varSymbol == null) {
            throw new SamanticError("Variable: " + varName + " is not declared", node.token.line, node.token.col);
        }

        return(null);
    }

}
