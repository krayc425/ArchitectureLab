package cn.edu.nju.sklnst.whilex;

public class OrExpression extends BoolExpression {

    public final BoolExpression e1;
    public final BoolExpression e2;

    public OrExpression(final BoolExpression e1, final BoolExpression e2) {
        super(e1, e2);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "or(" + e1 + "," + e2 + ")";
    }

    public void unparse(IndentPrinter p, Precedence precedence) {
        if (!precedence.atMost(Precedence.WHOLE)) {
            unparseParenthesized(p);
            return;
        }
        e1.unparse(p, Precedence.WHOLE);
        p.print(" or ");
        e2.unparse(p, Precedence.TERM);
    }
}
