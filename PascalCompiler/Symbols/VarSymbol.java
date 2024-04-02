package PascalCompiler.Symbols;

public class VarSymbol extends Symbol {
    public VarSymbol(String name, Symbol type) {
        super(name, type);
    }

    public String toString() {
        return("(Class: "+ this.getClass().getName() + "Name: " + this.name + ", Type: " + this.type.toString() + ")");
    }
}
