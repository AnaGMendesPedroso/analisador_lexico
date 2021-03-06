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
  public static int DOT = 27;

  public static String[] tokenNames = {
    "n/a",
    "<EOF>",
    "IDENTIFIER",
    "COMMA",
    "LBRACK",
    "RBRACK",
    "INTEGER_LITERAL",
    "KEYWORD",
    "PLUS",
    "MINUS",
    "TIMES",
    "DIVIDE",
    "ASSIGN",
    "LPAREN",
    "RPAREN",
    "SCOLON",
    "LBRACE",
    "RBRACE",
    "AND",
    "LESS",
    "HIGHER",
    "OR",
    "LEQ",
    "HEQ",
    "DIFF",
    "NOT",
    "EQUALS",
    "DOT",
  };

  public String getTokenName(int x) {
    return tokenNames[x];
  }

  public ListLexer(String input) {
    super(input);
  }

  void ErrorHandler() {
    System.out.println(
      "Linha: " + getCurrentLine() + " - Caractere ilegal: " + currentCharacter
    );
  }

  boolean isIGNOREDTOKEN() {
    return (
      currentCharacter == ' ' ||
      currentCharacter == '\t' ||
      currentCharacter == '\r'
    );
  }

  boolean isLETTER() {
    return (
      currentCharacter >= 'a' &&
      currentCharacter <= 'z' ||
      currentCharacter >= 'A' &&
      currentCharacter <= 'Z'
    );
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

    if (isKEYWORD(buf.toString())) return new Token(
      getCurrentLine(),
      KEYWORD,
      buf.toString()
    ); else return new Token(getCurrentLine(), IDENTIFIER, buf.toString());
  }

  boolean isKEYWORD(String keyword) {
    return (
      keyword.equals("class") ||
      keyword.equals("public") ||
      keyword.equals("static") ||
      keyword.equals("void") ||
      keyword.equals("String") ||
      keyword.equals("extends") ||
      keyword.equals("return") ||
      keyword.equals("int") ||
      keyword.equals("boolean") ||
      keyword.equals("if") ||
      keyword.equals("else") ||
      keyword.equals("while") ||
      keyword.equals("length") ||
      keyword.equals("true") ||
      keyword.equals("false") ||
      keyword.equals("this") ||
      keyword.equals("new") ||
      keyword.equals("System") ||
      keyword.equals("out") ||
      keyword.equals("println")
    );
  }

  // INTEGER_LITERAL : [0..9]+[0..9]*
  Token INTEGER_LITERAL() {
    StringBuilder buf = new StringBuilder();
    do {
      buf.append(currentCharacter);
      consume();
    } while (isNUMBER());
    return new Token(getCurrentLine(), INTEGER_LITERAL, buf.toString());
  }

  public Token nextToken() {
    while (currentCharacter != EOF) {
      switch (currentCharacter) {
        case ' ':
          WS();
          continue;
        case '\t':
          WS();
          continue;
        case '\n':
          consume();
          newLine();
          continue;
        case '\r':
          consume();
          newLine();
          continue;
        case ',':
          consume();
          return new Token(getCurrentLine(), COMMA, ",");
        case '[':
          consume();
          return new Token(getCurrentLine(), LBRACK, "[");
        case ']':
          consume();
          return new Token(getCurrentLine(), RBRACK, "]");
        case '+':
          consume();
          return new Token(getCurrentLine(), PLUS, "+");
        case '-':
          consume();
          return new Token(getCurrentLine(), MINUS, "-");
        case '*':
          consume();
          return new Token(getCurrentLine(), TIMES, "*");
        case '/':
          consume();
          if (currentCharacter == '/') {
            consume();
            newLine();
          }
          return new Token(getCurrentLine(), DIVIDE, "/");
        case '.':
          consume();
          return new Token(getCurrentLine(), DOT, ".");
        case '=':
          consume();
          return new Token(getCurrentLine(), ASSIGN, "=");
        case '(':
          consume();
          return new Token(getCurrentLine(), LPAREN, "(");
        case ')':
          consume();
          return new Token(getCurrentLine(), RPAREN, ")");
        case ';':
          consume();
          return new Token(getCurrentLine(), SCOLON, ";");
        case '{':
          consume();
          return new Token(getCurrentLine(), LBRACE, "{");
        case '}':
          consume();
          return new Token(getCurrentLine(), RBRACE, "}");
        case '<':
          consume();
          return new Token(getCurrentLine(), LESS, "<");
        case '>':
          consume();
          return new Token(getCurrentLine(), HIGHER, ">");
        case '!':
          consume();
          return new Token(getCurrentLine(), NOT, "!");
        case '?':
          consume();
          return new Token(getCurrentLine(),KEYWORD,"?");
        case '&':
          consume();
          if (currentCharacter == '&') {
            consume();
            return new Token(getCurrentLine(), AND, "&&");
          } else {
            returnCharacter();
            ErrorHandler();
        }
        default:
          if (isLETTER()) return IDENTIFIER();
          if (isNUMBER()) return INTEGER_LITERAL();
          consume();
      }
    }
    return new Token(getCurrentLine(), EOF_TYPE, "<EOF>");
  }

  /** WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace */
  void WS() {
    while (currentCharacter == ' ' || currentCharacter == '\t') consume();
  }
}
