# Lox


# Grammar

- **Literals** - Numbers, strings, Booleans, and nil
- **Unary expressions** - A prefix:
    - `!` = logical not
    - `-` = negate a number
- **Binary expressions** - The infix arithmentic (`+`, `-`, `*`, `/`), and logic (`==`, `!=`, `<`, `<=`, `>`, `>=`) operators
- **Parenthesis for grouping**


```
expression → literal
           | unary
           | binary
           | grouping ;

literal    → NUMBER | STRING | "true" | "false" | "nil" ;
grouping   → "(" expression ")" ;
unary      → ( "-" | "!" ) expression ;
binary     → expression operator expression ;
operator   → "==" | "!=" | "<" | "<=" | ">" | ">="
           | "+"  | "-"  | "*" | "/" ;
```

**Note on syntax**
- `*` = zero or more times
- `+` = at least once
- `?` = 0 or 1 times
