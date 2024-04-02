package Calculator;
public class Parser {
    Lexer lexer;
    Token currentToken;
    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = this.lexer.getNextToken();
    }

    private void eat(TokenTypes type) {
        if (this.currentToken.type == type) {
            this.currentToken = this.lexer.getNextToken();
        } else {
            throw new Error("Unexpected token type, was expecting: " + type + ", but was: " + this.currentToken.type);
        }
    }

    private AST factor() {
        Token token = this.currentToken;
        if (token.type == TokenTypes.PLUS) {
            this.eat(TokenTypes.PLUS);
            return(new UnaryOp(token, this.factor()));
        } else if (token.type == TokenTypes.MINUS) {
            this.eat(TokenTypes.MINUS);
            return(new UnaryOp(token, this.factor()));
        } else if (token.type == TokenTypes.INTEGER) {
            this.eat(TokenTypes.INTEGER);
            return(new Num(token));
        } else if (token.type == TokenTypes.LPAREN) {
            this.eat(TokenTypes.LPAREN);
            AST node = this.expr();
            this.eat(TokenTypes.RPAREN);
            return(node);
        }
        throw new Error();
    }

    private AST term() {
        AST node = this.factor();
        while(this.currentToken.type == TokenTypes.MUL || this.currentToken.type == TokenTypes.DIV) {
            Token token = this.currentToken;
            if (token.type == TokenTypes.MUL) {
                this.eat(TokenTypes.MUL);
            } else if (token.type == TokenTypes.DIV) {
                this.eat(TokenTypes.DIV);
            }

            node = new BinOp(node, token, this.factor());
        }

        return(node);

    }

    private AST expr() {
        AST node = this.term();

        while(this.currentToken.type == TokenTypes.PLUS || this.currentToken.type == TokenTypes.MINUS) {
            Token token = this.currentToken;

            if (token.type == TokenTypes.PLUS) {
                this.eat(TokenTypes.PLUS);
            } else if (token.type == TokenTypes.MINUS) {
                this.eat(TokenTypes.MINUS);
            }

            node = new BinOp(node, token, this.term());
        }
        return(node);
    }

    public AST parse() {
        return(this.expr());
    }
}