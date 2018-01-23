package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;

public class RelopExpression extends BoolExpression {

    public static enum Operator {LESS, EQUAL}

    ;

    public final Operator op;
    public final ArithmeticExpression e1;
    public final ArithmeticExpression e2;

    public RelopExpression(final Operator op, final ArithmeticExpression e1, final ArithmeticExpression e2) {
        super(e1, e2);
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return op + "(" + e1 + "," + e2 + ")";
    }

    public static Operator parseOperator(StreamTokenizer st) throws IOException {
        switch (st.nextToken()) {
            case '<':
                return Operator.LESS;
            case '=':
                return Operator.EQUAL;
            default:
                throw new ParseError("Expected ',' or '=': ", st);
        }
    }

    public void unparse(IndentPrinter p, Precedence unused) {
        e1.unparse(p);
        switch (op) {
            case LESS:
                p.print("<");
                break;
            case EQUAL:
                p.print("=");
                break;
        }
        e2.unparse(p);
    }
}
