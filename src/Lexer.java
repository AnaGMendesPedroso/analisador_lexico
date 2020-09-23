/***
 * Excerpted from "Language Implementation Patterns", published by The Pragmatic
 * Bookshelf. Copyrights apply to this code. It may not be used to create
 * training material, courses, books, articles, and the like. Contact us if you
 * are in doubt. We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book
 * information.
 ***/
public abstract class Lexer {
    public static final char EOF = (char) -1; // represent end of file char
    public static final int EOF_TYPE = 1; // represent EOF token type
    String input; // input string
    int p = 0; // index into input of current character
    int currentLine =0;
    char currentCharacter; // current character

    public Lexer(String input) {
        this.input = input;
       currentCharacter= input.charAt(p); // prime lookahead
    }

    /** Move one character; detect "end of file" */
    public void consume() {
        p++;
        if (p >= input.length())
           currentCharacter= EOF;
        else
           currentCharacter= input.charAt(p);
    }

    public void newLine(){
        this.currentLine++;
    }

    public int getCurrentLine(){
        return this.currentLine;
    }
    
    public int getCurrentCharacterPosition(){
        return this.p;
    }
    

    /** Ensure x is next character on the input stream */
    public void match(char x) {
        if (currentCharacter == x)
            consume();
        else
            throw new Error("expecting " + x + "; found " + currentCharacter);
    }

    public abstract Token nextToken();

    public abstract String getTokenName(int tokenType);
}
