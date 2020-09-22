/***
 * Excerpted from "Language Implementation Patterns", published by The Pragmatic
 * Bookshelf. Copyrights apply to this code. It may not be used to create
 * training material, courses, books, articles, and the like. Contact us if you
 * are in doubt. We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book
 * information.
 ***/
public class ListLexer extends Lexer {
    public static int IDENTIFIER = 2;
    public static int COMMA = 3;
    public static int LBRACK = 4;
    public static int RBRACK = 5;
    public static int INTEGER_LITERAL = 6;
    public static int KEYWORD = 7;
    public static int PLUS = 8;
    public static int MINUS = 9;
    public static int TIMES = 10;
    public static int DIVIDE = 11;
    public static int ASSIGN = 12;
    public static int LPAREN = 13;
    public static int RPAREN = 14;
    public static int SCOLON = 15;
    public static int LCURLYBRA = 16;
    public static int RCURLYBRA = 17;

    public static String[] tokenNames = { "n/a", "<EOF>", "IDENTIFIER", "COMMA", "LBRACK", "RBRACK" , "INTEGER_LITERAL", "KEYWORD", 
                "PLUS", "MINUS", "TIMES", "DIVIDE", "ASSIGN","LPAREN","RPAREN","SCOLON","LCURLYBRA","RCURLYBRA"};

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
    
    /** NAME : ('a'..'z'|'A'..'Z')+; // NAME is sequence of >=1 letter */
    Token IDENTIFIER() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(currentCharacter);
            consume();
        } while (isLETTER());
        return new Token(IDENTIFIER, buf.toString());
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
                case '+':
                    consume();
                    return new Token(PLUS, "+");
                case '-':
                    consume();
                    return new Token(MINUS, "-");
                case '*':
                    consume();
                    return new Token(TIMES, "*");
                case '/':
                    consume();
                    return new Token(DIVIDE, "/");
                case '=':
                    consume();
                    return new Token(ASSIGN, "=");
                case '(':
                    consume();
                    return new Token(LPAREN, "(");
                case ')':
                    consume();
                    return new Token(RPAREN, ")");
                case ';':
                    consume();
                    return new Token(SCOLON, ";");       
                case '{':
                    consume();
                    return new Token(LCURLYBRA, "{");  
                case '}':
                    consume();
                    return new Token(RCURLYBRA, "}");  
                default:
                    if (isLETTER())
                        return IDENTIFIER();
                    if (isNUMBER())
                        return INTEGER_LITERAL();
                    throw new Error("invalid character: " + currentCharacter);
            }
        }
        return new Token(EOF_TYPE, "<EOF>");
    }
        
    /** WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace */
    void WS() {
        while (currentCharacter == ' ' ||currentCharacter== '\t' ||currentCharacter== '\n' ||currentCharacter== '\r')
            consume();
    }
}
