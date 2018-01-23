package cn.edu.nju.sklnst.whilex;

public class NotExpression extends BoolExpression {

    public final BoolExpression e;

    public NotExpression(final BoolExpression e) {
        super(e);
        this.e = e;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        return "not(" + e + ")";
    }

    @Override
    public void unparse(IndentPrinter p, Precedence precedence) {
        p.print("not ");
        e.unparse(p, Precedence.FACTOR);
    }

}
