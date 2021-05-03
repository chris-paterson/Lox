# Lox

My implementation to [Crafting Interpreters](https://craftinginterpreters.com/). Includes all challenges. 

# Grammar

- **Literals** - Numbers, strings, Booleans, and nil
- **Unary expressions** - A prefix:
    - `!` = logical not
    - `-` = negate a number
- **Binary expressions** - The infix arithmentic (`+`, `-`, `*`, `/`), and logic (`==`, `!=`, `<`, `<=`, `>`, `>=`) operators
- **Parenthesis for grouping**


```
program         → statement* EOF ;
declaration     → varDecl
                | statement ;
varDecl         → "var" IDENTIFIER ( "=" expression )? ";"
statement       → exprStmt
                | printStmt ;
exprStmt        → expression ";" ;
printStmt       → "print" expression ";" ;
expression      → assignment ;
assignment      → IDENTIFIER "=" assignment
                | comma ;
comma           → ternary ("," ternary)* ;
ternary         → equality "?" expression ":" ternary ;
equality        → comparison ( ( "!=" | "==" ) comparison )* ;
comparison      → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term            → factor ( ( "-" | "+" ) factor )* ;
factor          → unary ( ( "/" | "*" ) unary )* ;
unary           → ( "!" | "-" ) unary
                | primary ;
primary         → NUMBER | STRING | "true" | "false" | "nil"
                | "(" expression ")" 
                | IDENTIFIER ;
```

**Note on syntax**
- `*` = zero or more times
- `+` = at least once
- `?` = 0 or 1 times
