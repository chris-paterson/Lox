# Lox


# Grammar

- **Literals** - Numbers, strings, Booleans, and nil
- **Unary expressions** - A prefix:
    - `!` = logical not
    - `-` = negate a number
- **Binary expressions** - The infix arithmentic (`+`, `-`, `*`, `/`), and logic (`==`, `!=`, `<`, `<=`, `>`, `>=`) operators
- **Parenthesis for grouping**


```
expression     → equality ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;
comparison     → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
addition       → multiplication ( ( "-" | "+" ) multiplication )* ;
multiplication → unary ( ( "/" | "*" ) unary )* ;
unary          → ( "!" | "-" ) unary
               | primary ;
primary        → NUMBER | STRING | "false" | "true" | "nil"
               | "(" expression ")" ;
```

**Note on syntax**
- `*` = zero or more times
- `+` = at least once
- `?` = 0 or 1 times
