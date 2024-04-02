package PascalCompiler.Symbols;
import java.util.LinkedHashMap;

public class SymbolTable {

    LinkedHashMap<String, Symbol> symbols = new LinkedHashMap<>();

    public SymbolTable() {
        this.initBuiltins();
    }

    private void initBuiltins() {
        this.insert(new BuiltInSymbol("INTEGER"));
        this.insert(new BuiltInSymbol("REAL"));
    }

    public void print() {
        System.out.println("Symbols: " + this.symbols.toString());
    }

    public void insert(Symbol symbol) {
        System.out.println("Define: " + symbol.toString());
        this.symbols.put(symbol.name, symbol);
    }

    public Symbol lookup(String name) {
        System.out.println("Lookup: " + name);
        return(this.symbols.get(name));
    }
}