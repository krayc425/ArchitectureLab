package cn.edu.nju.sklnst.whilex;

import java.io.IOException;
import java.io.StreamTokenizer;

public class BinopExpression extends ArithmeticExpression {

    public static enum Operator {
        PLUS {
            public Precedence left() {
                return Precedence.WHOLE;
            }

            public Precedence right() {
                return Precedence.TERM;
            }
        },
        MINUS {
            public Precedence left() {
                return Precedence.WHOLE;
            }

            public Precedence right() {
                return Precedence.TERM;
            }
        },
        TIMES {
            public Precedence left() {
                return Precedence.TERM;
            }

            public Precedence right() {
                return Precedence.FACTOR;
            }
        },
        DIVIDE {
            public Precedence left() {
                return Precedence.TERM;
            }

            public Precedence right() {
                return Precedence.FACTOR;
            }
        };

        public abstract Precedence left();

        public abstract Precedence right();
    }

    public final Operator op;
    public final ArithmeticExpression e1;
    public final ArithmeticExpression e2;

    public BinopExpression(final Operator op, final ArithmeticExpression e1, final ArithmeticExpression e2) {
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
            case '+':
                return Operator.PLUS;
            case '-':
                return Operator.MINUS;
            case '*':
                return Operator.TIMES;
            case '/':
                return Operator.DIVIDE;
            default:
                throw new ParseError("Expected binary operator: +-*/");
        }
    }

    @Override
    public void unparse(IndentPrinter p, Precedence precedence) {
        if (!precedence.atMost(op.left())) {
            unparseParenthesized(p);
            return;
        }
        e1.unparse(p, op.left());
        switch (op) {
            case PLUS:
                p.print("+");
                break;
            case MINUS:
                p.print("-");
                break;
            case TIMES:
                p.print("*");
                break;
            case DIVIDE:
                p.print("/");
                break;
        }
        e2.unparse(p, op.right());
    }


}
