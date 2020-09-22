/***
 * Excerpted from "Language Implementation Patterns", published by The Pragmatic
 * Bookshelf. Copyrights apply to this code. It may not be used to create
 * training material, courses, books, articles, and the like. Contact us if you
 * are in doubt. We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book
 * information.
 ***/
public class ListLexer extends Lexer {
    public static int NAME = 2;
    public static int COMMA = 3;
    public static int LBRACK = 4;
    public static int RBRACK = 5;
    public static int INTEGER_LITERAL = 6;
    public static int KEYWORD = 7;
    public static String[] tokenNames = { "n/a", "<EOF>", "NAME", "COMMA", "LBRACK", "RBRACK" , "INTEGER_LITERAL", "KEYWORD"};

    public String getTokenName(int x) {
        return tokenNames[x];
    }

    public ListLexer(String input) {
        super(input);
    }

    boolean isLETTER() {
        return currentCharacter >= 'a' && currentCharacter <= 'z' ||currentCharacter>= 'A' &&currentCharacter<= 'Z';
    }

    boolean isNUMBER() {
        return currentCharacter >= '0' && currentCharacter <= '9';
    }
    
    public Token nextToken() {
        while (currentCharacter != EOF) {
            switch (currentCharacter) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    WS();
                    continue;
                case ',':
                    consume();
                    return new Token(COMMA, ",");
                case '[':
                    consume();
                    return new Token(LBRACK, "[");
                case ']':
                    consume();
                    return new Token(RBRACK, "]");
                default:
                    if (isLETTER())
                        return NAME();
                    if (isNUMBER())
                        return INTEGER_LITERAL();
                    /*if ()
                        return KEYWORD();*/
                    throw new Error("invalid character: " + currentCharacter);
            }
        }
        return new Token(EOF_TYPE, "<EOF>");
    }

    /** NAME : ('a'..'z'|'A'..'Z')+; // NAME is sequence of >=1 letter */
    Token NAME() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(currentCharacter);
            consume();
        } while (isLETTER());
        return new Token(NAME, buf.toString());
    }


    // INTEGER_LITERAL : [0..9]+[0..9]* 
    Token INTEGER_LITERAL() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(currentCharacter);
            consume();
        } while (isNUMBER());
        return new Token(INTEGER_LITERAL, buf.toString());
    }
    
   /* Token KEYWORD() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(currentCharacter);
            consume();
        } while (SymbolTable.contains(currentCharacter));
        return new Token(KEYWORD, buf.toString());
    }
    */
    
    /** WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace */
    void WS() {
        while (currentCharacter == ' ' ||currentCharacter== '\t' ||currentCharacter== '\n' ||currentCharacter== '\r')
            consume();
    }
}
