package PascalCompiler.Symbols;

public class BuiltInSymbol extends Symbol {
    public BuiltInSymbol(String name) {
        super(name);
    }

    public String toString() {
        return(this.name);
    }
}
