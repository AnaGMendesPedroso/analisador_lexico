public class SymbolTable {
  private static String[] keywords = {
    "class",
    "public",
    "static",
    "void",
    "main",
    "String",
    "return",
    "int",
    "boolean",
    "while",
    "System.out.println",
    "if",
    "else",
    "this",
    "length",
    "new",
    "extends",
    "true",
    "false",
  };

  public static boolean contains(char word) {
    boolean result = false;
    for (int i = 0; i < keywords.length; i++) {
      if (keywords[i].equals(word)) {
        result = true;
        break;
      }
    }
    return result;
  }
}
