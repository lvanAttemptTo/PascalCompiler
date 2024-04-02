package PascalCompiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.util.Scanner;

import PascalCompiler.Compiler.Interpreter;
import PascalCompiler.Compiler.Lexer;
import PascalCompiler.Compiler.Parser;
import PascalCompiler.Compiler.SamanticAnalyzer;
import PascalCompiler.Compiler.SymbolTableBuilder;
import PascalCompiler.Globals.Global;
import PascalCompiler.Globals.Keywords;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        /*
         * Grammars:
         * 
         * program : PROGRAM variable SEMI block DOT
         * 
         * block: declarations compound_statement
         * 
         * declarations: VAR(variable_declaration SEMI)+ | empty
         * 
         * variable_declaration: ID (COMMA ID)* COLON type_spec
         * 
         * type_spec: INTEGER | REAL
         * 
         * compound_statement : BEGIN statement_ist END
         * 
         * statement_list : statement | statement SEMI statement_list
         * 
         * statement : compound_statement | assignment_statement | empty
         * 
         * assignment_statement : variable ASSIGN expr
         * 
         * variable : ID
         * 
         * empty :
         * 
         * expr : term ((PLUS | MINUS) term)*
         * 
         * term : factor ((MUL|DIV|INTEGER_DIV) factor)*
         * 
         * factor: PLUS factor | MINUS factor | INTEGER_CONST | REAL_CONST | LPAREN expr RPAREN | variable
         * 
         */
      System.out.println('\n');
      try {
         File myObj = new File("PascalCompiler/test.pas");
         StringBuilder code = new StringBuilder();
         Scanner myReader = new Scanner(myObj);
         while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            code.append(data);
            code.append('\n');
            
         }
         
         myReader.close();
         new Keywords();
         Lexer lexer = new Lexer(code.toString());
         Parser parser = new Parser(lexer);
         Interpreter interpreter = new Interpreter(parser);
         SamanticAnalyzer samanticAnalyzer = new SamanticAnalyzer();
         samanticAnalyzer.visit(new Parser(new Lexer(code.toString())).parse());
         interpreter.interpret();
         System.out.println(Global.vars);
      } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   
       
      // SymbolTable symTab = new SymbolTable();
      // BuiltInSymbol int_type = new BuiltInSymbol("INTEGER");
      // symTab.define(int_type);
      // symTab.define(new VarSymbol("x", int_type));
      // BuiltInSymbol real_type = new BuiltInSymbol("REAL");
      // symTab.define(real_type);
      // symTab.define(new VarSymbol("y", real_type));
      // symTab.print();
      
   }
}
