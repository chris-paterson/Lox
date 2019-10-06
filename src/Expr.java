abstract class Expr {
    static class Binary extends Expr {

        final Expr left;
        final Token operator;
        final Expr right;

        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }

    static class Unary extends Expr {

        final Token operator;
        final Expr right;

        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }
    }


    static class Operator extends Expr {

        final Token operator;

        Operator(Token operator) {
            this.operator = operator;
        }
    }
}
