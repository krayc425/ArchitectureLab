package cn.edu.nju.sklnst.whilex;

public class AndExpression extends BoolExpression {

    public final BoolExpression e1;
    public final BoolExpression e2;

    public AndExpression(final BoolExpression e1, final BoolExpression e2) {
        super(e1, e2);
        this.e1 = e1;
        this.e2 = e2;
    }


    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "and(" + e1 + "," + e2 + ")";
    }

    public void unparse(IndentPrinter p, Precedence precedence) {
        if (!precedence.atMost(Precedence.TERM)) {
            unparseParenthesized(p);
            return;
        }
        e1.unparse(p, Precedence.TERM);
        p.print(" and ");
        e2.unparse(p, Precedence.FACTOR);
    }
}
