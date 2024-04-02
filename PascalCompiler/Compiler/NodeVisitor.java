package PascalCompiler.Compiler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import PascalCompiler.ASTNodes.AST;
import PascalCompiler.ASTNodes.Assign;
import PascalCompiler.ASTNodes.BinOp;
import PascalCompiler.ASTNodes.Block;
import PascalCompiler.ASTNodes.Compound;
import PascalCompiler.ASTNodes.NoOp;
import PascalCompiler.ASTNodes.Num;
import PascalCompiler.ASTNodes.ProcedureDec;
import PascalCompiler.ASTNodes.Program;
import PascalCompiler.ASTNodes.Type;
import PascalCompiler.ASTNodes.UnaryOp;
import PascalCompiler.ASTNodes.Var;
import PascalCompiler.ASTNodes.VarDec;
import PascalCompiler.Globals.Global;
import PascalCompiler.Token.TokenType;

public class NodeVisitor {
    public Double visit(AST node) {
        if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Num")) {
            return(this.visitNum((Num)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.BinOp")) {
            return(this.visitBinOp((BinOp)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.UnaryOp")) {
            return(this.visitUnaryOp((UnaryOp)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Compound")) {
            return(this.visitCompound((Compound)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Assign")) {
            return(this.visitAssign((Assign)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Var")) {
            return(this.visitVar((Var)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Program")) {
            return(this.visitProgram((Program)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Block")) {
            return(this.visitBlock((Block)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.VarDec")) {
            return(this.visitDec((VarDec)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.Type")) {
            return(this.visitType((Type)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.NoOp")) {
            return(this.visitNoOp((NoOp)node));
        } else if (node.getClass().getName().equals("PascalCompiler.ASTNodes.ProcedureDec")) {
            return(this.visitProcedureDec((ProcedureDec)node));
        }
        System.out.println(node.getClass().getName());
        throw new Error(node.getClass().getName());
    }

    public Double visitNum(Num node) {
        return(node).getValue();
    }

    public Double visitBinOp(BinOp node) {
        
        if (node.op.type == TokenType.PLUS) {
            return(this.visit(node.left) + this.visit(node.right));
        } else if (node.op.type == TokenType.MINUS) {
            return(this.visit(node.left) - this.visit(node.right));
        } else if (node.op.type == TokenType.REAL_DIV) {
            return(this.visit(node.left) / this.visit(node.right));
        } else if (node.op.type == TokenType.INTEGER_DIV) {
            return((double)Math.round(this.visit(node.left) / this.visit(node.right)));
        } else if (node.op.type == TokenType.MUL) {
            return(this.visit(node.left) * this.visit(node.right));
        }
        throw new Error();
    }

    public Double visitUnaryOp(UnaryOp node) {
        
        TokenType op = node.op.type;
        if (op == TokenType.PLUS) {
            return(+this.visit(node.expr));
        } else if (op == TokenType.MINUS) {
            return(-this.visit(node.expr));
        }
        return(null);
    }

    public Double visitCompound(Compound node) {
        for (int i = 0; i < node.children.size(); i++) {
            this.visit(node.children.get(i));
        }
        return(null);
    }

    public Double visitAssign(Assign node) {
        String varName = node.left.value;
        double value = this.visit(node.right);
        Global.vars.put(varName, value);
        System.out.println(varName + ", " + value);
        return(null);
    }

    public Double visitVar(Var node) {
        String varName = node.value;
        Double value = Global.vars.get(varName);
        
        return(value);
    }

    public Double visitProgram(Program node) {
        this.visit(node.block);
        return(null);
    }

    public Double visitBlock(Block node) {
        for (int i = 0; i < node.declarations.length; i++) {
            this.visit(node.declarations[i]);
        }
        this.visit(node.compoundStatement);
        return(null);
    }

    public Double visitDec(VarDec node) {
        return(null);
    }

    public Double visitType(Type node) {
        return(null);
    }

    public Double visitNoOp(NoOp node) {
        return(null);
    }

    public Double visitProcedureDec(ProcedureDec node) {
        return(null);
    }
}



