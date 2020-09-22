/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {

    public static void main(String[] args) {
        //ListLexer lexer = new ListLexer(args[0]);
        //File entrada = new File(/*scan.next()*/"teste.txt");
        try {
            String content = Files.readString(Paths.get(args[0]));=
            ListLexer lexer = new ListLexer(content);
            Token t = lexer.nextToken();
            while ( t.type != Lexer.EOF_TYPE ) {
                System.out.println(t);
                t = lexer.nextToken();
            }
            System.out.println(t); // EOF
        } 
    
        catch (IOException e) {
            e.printStackTrace();      
        }
    }
}
