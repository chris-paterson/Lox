public class Token {
    final TokenType type; // The type of lexeme.
    final String lexeme; // [var] [language] [=] ["lox"] [;]
    final Object literal; // Text representation, e.g. let ["language"] = ["lox"];
    final int line;

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " | " + lexeme + " | " + literal;
    }
}
