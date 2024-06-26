package PascalCompiler.Compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import PascalCompiler.ASTNodes.*;
import PascalCompiler.Errors.ParserError;
import PascalCompiler.Token.Token;
import PascalCompiler.Token.TokenType;

public class Parser {
    Lexer lexer;
    Token currentToken;
    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = this.lexer.getNextToken();
    }

    private void eat(TokenType type) {
        if (this.currentToken.type == type) {
            this.currentToken = this.lexer.getNextToken();
        } else {
            throw new ParserError(String.format("Unexpected token type, was expecting: %s, but was: %s", type, this.currentToken.type), this.currentToken.line, this.currentToken.col);
        }
    }

    private Program program() {
        this.eat(TokenType.PROGRAM);
        Var varNode = this.variable();
        String programName = varNode.value;
        this.eat(TokenType.SEMI);
        Block blockNode = this.block();
        Program programNode = new Program(programName, blockNode);
        this.eat(TokenType.DOT);
        return(programNode);
    }

    private Compound compoundStatement() {
        this.eat(TokenType.BEGIN);
        ArrayList<AST> nodes = this.statementList();
        this.eat(TokenType.END);

        Compound root = new Compound();
        for (int i = 0; i < nodes.size(); i++) {
            root.children.add(nodes.get(i));
        }

        return(root);

    }

    private ArrayList<AST> statementList() {
        AST node = this.statement();

        ArrayList<AST> result = new ArrayList<>();

        result.add(node);

        while (this.currentToken.type == TokenType.SEMI) {
            this.eat(TokenType.SEMI);
            result.add(this.statement());
        }

        if (this.currentToken.type == TokenType.ID) {
            throw new ParserError("ID in illegal place", this.currentToken.line, this.currentToken.col);
        }

        return(result);
    }

    private AST statement() {
        AST node;
        if (this.currentToken.type == TokenType.BEGIN) {
            node = this.compoundStatement();
        } else if (this.currentToken.type == TokenType.ID && this.lexer.currentChar == '(') {
            node = this.procCall();
        } else if (this.currentToken.type == TokenType.ID) {
            node = this.assignStatement();
        } else {
            node = this.empty();
        }
        return(node);
    }

    private AST assignStatement() {
        Var left = this.variable();
        Token token = this.currentToken;
        this.eat(TokenType.ASSIGN);
        AST right = this.expr();
        Assign node = new Assign(left, token, right);
        return(node);

    }

    private Var variable() {
        Var node = new Var(this.currentToken);
        this.eat(TokenType.ID);
        return(node);
    }

    private NoOp empty() {
        return(new NoOp());
    }

    private AST factor() {
        Token token = this.currentToken;
        if (token.type == TokenType.PLUS) {
            this.eat(TokenType.PLUS);
            return(new UnaryOp(token, this.factor()));
        } else if (token.type == TokenType.MINUS) {
            this.eat(TokenType.MINUS);
            return(new UnaryOp(token, this.factor()));
        } else if (token.type == TokenType.INTEGER_CONST) {
            this.eat(TokenType.INTEGER_CONST);
            return(new Num(token));
        } else if (token.type == TokenType.REAL_CONST) {
            this.eat(TokenType.REAL_CONST);
            return(new Num(token));
        } else if (token.type == TokenType.LPAREN) {
            this.eat(TokenType.LPAREN);
            AST node = this.expr();
            this.eat(TokenType.RPAREN);
            return(node);
        } else {
            AST node = this.variable();
            return(node);
        }
    }

    private AST term() {
        AST node = this.factor();
        while(this.currentToken.type == TokenType.MUL || this.currentToken.type == TokenType.REAL_DIV || this.currentToken.type == TokenType.DIV) {
            Token token = this.currentToken;
            if (token.type == TokenType.MUL) {
                this.eat(TokenType.MUL);
            } else if (token.type == TokenType.REAL_DIV) {
                this.eat(TokenType.REAL_DIV);
            } else if (token.type == TokenType.DIV) {
                this.eat(TokenType.DIV);
            }

            node = new BinOp(node, token, this.factor());
        }

        return(node);

    }

    private AST expr() {
        AST node = this.term();

        while(this.currentToken.type == TokenType.PLUS || this.currentToken.type == TokenType.MINUS) {
            Token token = this.currentToken;

            if (token.type == TokenType.PLUS) {
                this.eat(TokenType.PLUS);
            } else if (token.type == TokenType.MINUS) {
                this.eat(TokenType.MINUS);
            }

            node = new BinOp(node, token, this.term());
        }
        return(node);
    }

    private Block block() {
        Dec[] decNodes = this.declarations();
        Compound compNode = this.compoundStatement();
        return(new Block(decNodes, compNode));

    }

    private Dec[] declarations() {
        ArrayList<Dec> decs = new ArrayList<>();
        while (this.currentToken.type == TokenType.VAR) {
            this.eat(TokenType.VAR);
            while (this.currentToken.type == TokenType.ID) {
                VarDec[] varDecs = this.variableDeclarations();
                for (int i = 0; i < varDecs.length; i++) {
                    decs.add(varDecs[i]);
                }
                this.eat(TokenType.SEMI);
            }
        }

        while (this.currentToken.type == TokenType.PROCEDURE) {
            ProcedureDec procDec = this.procedureDeclaration();
            decs.add(procDec);
        }

        Dec[] result = new Dec[decs.size()];
        for (int i = 0; i < decs.size(); i++) {
            result[i] = decs.get(i);
        }
        return(result);
    }

    private ProcedureDec procedureDeclaration() {
        this.eat(TokenType.PROCEDURE);
        String procName = this.currentToken.value;
        this.eat(TokenType.ID);
        AST[] params = null;
        // ArrayList<AST> params = new ArrayList<>();
        if (this.currentToken.type == TokenType.LPAREN) {
            this.eat(TokenType.LPAREN);
            params = this.formalParamsList();
            this.eat(TokenType.RPAREN);
        }

        this.eat(TokenType.SEMI);
        Block block = this.block();
        this.eat(TokenType.SEMI);
        return(new ProcedureDec(procName, block, params));
        
    }

    private ArrayList<Param> formalParams() {
        ArrayList<Param> paramNodes = new ArrayList<>();

        ArrayList<Token> paramTokens = new ArrayList<>();
        paramTokens.add(this.currentToken);
        this.eat(TokenType.ID);
        while (this.currentToken.type == TokenType.COMMA) {
            this.eat(TokenType.COMMA);
            paramTokens.add(this.currentToken);
            this.eat(TokenType.ID);
        }

        this.eat(TokenType.COLON);

        Type typeNode = this.typeSpec();
        for (Token t:paramTokens) {
            paramNodes.add(new Param(new Var(t), typeNode));
        }

        return(paramNodes);
    }

    private Param[] formalParamsList() {
        if (this.currentToken.type != TokenType.ID) {
            return(null);
        }
        ArrayList<Param> paramNodes = this.formalParams();

        while (this.currentToken.type == TokenType.SEMI) {
            this.eat(TokenType.SEMI);
            ArrayList<Param> temp = this.formalParams();
            for (Param p:temp) {
                paramNodes.add(p);
            }
            
        }

        Param[] result = new Param[paramNodes.size()];
        for (int i = 0; i < paramNodes.size(); i++) {
            result[i] = paramNodes.get(i);
        }
        
        return(result);
    }

    private VarDec[] variableDeclarations() {
        ArrayList<Var> varNodes = new ArrayList<>();

        varNodes.add(new Var(this.currentToken));
        this.eat(TokenType.ID);

        while(this.currentToken.type == TokenType.COMMA) {
            this.eat(TokenType.COMMA);
            varNodes.add(new Var(this.currentToken));
            this.eat(TokenType.ID);
        }

        this.eat(TokenType.COLON);

        Type typeNode = this.typeSpec();
        VarDec[] varDecs = new VarDec[varNodes.size()];

        for (int i = 0; i < varNodes.size(); i++) {
            varDecs[i] = new VarDec(varNodes.get(i), typeNode);
        }

        return(varDecs);
    }

    private ProcedureCall procCall() {
        Token token = this.currentToken;

        String procName = this.currentToken.value;
        this.eat(TokenType.ID);
        this.eat(TokenType.LPAREN);
        ArrayList<AST> params = new ArrayList<>();
        if (this.currentToken.type != TokenType.RPAREN) {
            AST node = this.expr();
            params.add(node);
        }

        while(this.currentToken.type == TokenType.COMMA) {
            this.eat(TokenType.COMMA);
            AST node = this.expr();
            params.add(node);
        }
        this.eat(TokenType.RPAREN);

        AST[] endParams = new AST[params.size()];
        for (int i = 0; i < params.size(); i++) {
            endParams[i] = params.get(i);
        }

        return(new ProcedureCall(procName, endParams, token));
    }

    private Type typeSpec() {
        Token token = this.currentToken;
        if (this.currentToken.type == TokenType.INTEGER) {
            this.eat(TokenType.INTEGER);
        } else {
            this.eat(TokenType.REAL);
        }

        Type node = new Type(token);
        return(node);
    }

    public AST parse() {
        AST node = this.program();
        if (this.currentToken.type != TokenType.EOF) {
            throw new ParserError("Dot before end of file", this.currentToken.line, this.currentToken.col);
        }
        return(node);
    }
}