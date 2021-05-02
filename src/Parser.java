import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static class ParseError extends RuntimeException { }

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(statement());
        }
        return statements;
    }

    private Stmt statement() {
        if (match(TokenType.PRINT)) return printStatement();
        return expressionStatement();
    }

    private Stmt expressionStatement() {
        Expr expr = expression();
        consume(TokenType.SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(expr);
    }

    private Stmt printStatement() {
        Expr value = expression();
        consume(TokenType.SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(value);
    }

    private Expr expression() {
        return comma();
    }

    // Evaluate from lowest to highest precedence.
    private Expr comma() {
        Expr expr = ternary(); // Evaluate left and discard.

        while (match(TokenType.COMMA)) {
            expr = ternary();
        }

        return expr;
    }

    private Expr ternary() {
        Expr expr = equality();

        while (match(TokenType.QUESTION_MARK)) {
            Expr trueExpr = expression();
            consume(TokenType.COLON, "Expect ':' after expression.");
            Expr falseExpr = ternary();
            expr = new Expr.Ternary(expr, trueExpr, falseExpr);
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();
        if (expr == null) {
            comparison(); // Parse and discard RHS.
            throw error(peek(), "Binary expressions should start with a left hand operand.");
        }

        while (match(TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = term();
        if (expr == null) {
            term(); // Parse and discard RHS.
            throw error(peek(), "Binary expressions should start with a left hand operand.");
        }

        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    // Addition / Subtraction.
    private Expr term() {
        Expr expr = factor();
        if (expr == null) {
            factor(); // Parse and discard RHS.
            throw error(peek(), "Binary expressions should start with a left hand operand.");
        }

        while (match(TokenType.MINUS, TokenType.PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    // Multiplication / Division.
    private Expr factor() {
        Expr expr = unary();
        if (expr == null) {
            unary(); // Parse and discard RHS.
            throw error(peek(), "Binary expressions should start with a left hand operand.");
        }

        while (match(TokenType.SLASH, TokenType.STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }


    private Expr unary() {
        while (match(TokenType.BANG, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = primary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }


     private Expr primary() {
         if (match(TokenType.FALSE)) return new Expr.Literal(false);
         if (match(TokenType.TRUE)) return new Expr.Literal(true);
         if (match(TokenType.NIL)) return new Expr.Literal(null);

         if (match(TokenType.NUMBER, TokenType.STRING)) {
             return new Expr.Literal(previous().literal);
         }

         if (match(TokenType.LEFT_PAREN)) {
           Expr expr = expression();
           consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.");
           return new Expr.Grouping(expr);
         }

         return null;
     }


    // Consumes tokens.
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }


    private Token consume(TokenType type, String message) {
        if (check(type)) { return advance(); }

        throw error(peek(), message);
    }

    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == TokenType.SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }


    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    // See if we've run out of tokens to consume.
    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    // Current token yet to be consumed.
    private Token peek() {
        return tokens.get(current);
    }

    // Most recently consumed token.
    private Token previous() {
        return tokens.get(current - 1);
    }

}
