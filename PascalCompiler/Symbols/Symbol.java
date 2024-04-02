package PascalCompiler.Symbols;

public class Symbol {
    String name;
    Symbol type = null;
    public Symbol(String name, Symbol type) {
        this.name = name;
        this.type = type;
    }

    public Symbol(String name) {
        this.name = name;
    }


    public String toString() {
        return("(Class: "+ this.getClass().getName() + "Name: " + this.name + ", Type: " + this.type.toString() + ")");
    }
}