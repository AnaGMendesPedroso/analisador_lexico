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
    public static int LBRACE = 16;
    public static int RBRACE = 17;
    public static int AND = 18;
    public static int LESS = 19;
    public static int HIGHER = 20;
    public static int OR = 21;
    public static int LEQ = 22;
    public static int HEQ = 23;
    public static int DIFF = 24;
    public static int NOT = 25;
    public static int EQUALS = 26;

    public static String[] tokenNames = { "n/a", "<EOF>", "IDENTIFIER", "COMMA", "LBRACK", "RBRACK" , "INTEGER_LITERAL", "KEYWORD", 
            "PLUS", "MINUS", "TIMES", "DIVIDE", "ASSIGN","LPAREN","RPAREN","SCOLON","LBRACE","RBRACE","AND","LESS","HIGHER", "OR", "LEQ", "HEQ", "DIFF", "NOT", "EQUALS"};

    public String getTokenName(int x) {
        return tokenNames[x];
    }

    public ListLexer(String input) {
        super(input);
    }

    void ErrorHandler(){
        throw new Error("Invalid character found: \"" + currentCharacter+"\" in line "+ getCurrentLine()+ " at Index "+getCurrentCharacterPosition());
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
                    newLine();
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
                    if (currentCharacter == '=') {
                        consume();
                        return new Token(EQUALS, "==");
                    }else if (isNUMBER() || isLETTER()){                        
                        return new Token(ASSIGN, "=");
                    }else if(currentCharacter == ' '){
                        consume();
                        if (isNUMBER() || isLETTER()){                        
                            return new Token(ASSIGN, "=");
                        }else{
                             ErrorHandler();
                        }
                    }else{
                         ErrorHandler();
                    }

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
                    return new Token(LBRACE, "{");  

                case '}':
                    consume();
                    return new Token(RBRACE, "}");

                case '&':
                    consume();
                    if (currentCharacter == '&'){
                        consume();
                        return new Token(AND, "&&");
                    }else{
                         ErrorHandler();
                    }

                case '|'    :
                    consume();
                    if (currentCharacter == '|'){
                        return new Token(OR, "||");
                    }

                case '<':
                    consume();
                    if (currentCharacter == '='){
                        consume();
                        return new Token(LEQ, "<=");
                    }else if(currentCharacter == ' '){
                        consume();
                    }else if (isNUMBER() || isLETTER()){ 
                        consume();
                            return new Token(LESS, "<");
                    }else{
                             ErrorHandler();
                    }

                case '>':
                    consume();
                    if (currentCharacter == '='){
                        consume();
                        return new Token(HEQ, ">=");
                    }else if(currentCharacter==' '){
                        consume();
                    }else if(isNUMBER()||isLETTER()){
                       return new Token(HIGHER, ">");
                    }else{
                       ErrorHandler();
                    }                   

                case '!':
                    consume();
                    if (currentCharacter == '='){
                        consume();
                        return new Token(DIFF, "!=");
                    }else if (isLETTER()) {
                        return new Token(NOT, "!");
                    }else if(currentCharacter == ' '){
                        consume();
                        if (isLETTER()) {
                            return new Token(NOT, "!");
                        }else{
                             ErrorHandler();
                        }
                    }else{
                         ErrorHandler();
                    }
                    
                default:
                    if (isLETTER())
                        return IDENTIFIER();
                    if (isNUMBER())
                        return INTEGER_LITERAL();
                     ErrorHandler();
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
