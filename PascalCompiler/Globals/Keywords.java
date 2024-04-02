package PascalCompiler.Globals;
import java.util.Arrays;
import java.util.HashMap;

import Calculator.TokenTypes;
import PascalCompiler.Token.Token;
import PascalCompiler.Token.TokenType;

public class Keywords {
    public static HashMap<String, Token> map = new HashMap<>();
    
    public Keywords() {
        // Keywords.map.put("BEGIN", new Token(TokenType.BEGIN, null));
        // Keywords.map.put("END", new Token(TokenType.END, null));
        // Keywords.map.put("PROGRAM", new Token(TokenType.PROGRAM, null));
        // Keywords.map.put("VAR", new Token(TokenType.VAR, null));
        // Keywords.map.put("INTEGER", new Token(TokenType.INTEGER, null));
        // Keywords.map.put("REAL", new Token(TokenType.REAL, null));
        // Keywords.map.put("DIV", new Token(TokenType.INTEGER_DIV, null));
        // Keywords.map.put("PROCEDURE", new Token(TokenType.PROCEDURE, null));

        TokenType[] resWords = TokenType.values();
        for (int i = Arrays.binarySearch(resWords, TokenType.BEGIN); i <= Arrays.binarySearch(resWords, TokenType.END); i++) {
            Keywords.map.put(resWords[i].toString(), new Token(resWords[i], null));
        }
    }
}
